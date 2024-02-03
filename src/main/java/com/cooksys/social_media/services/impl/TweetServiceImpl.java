package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.dtos.*;
import com.cooksys.social_media.entities.Credentials;
import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;

import com.cooksys.social_media.mappers.CredentialsMapper;
import com.cooksys.social_media.mappers.HashtagMapper;
import com.cooksys.social_media.mappers.TweetMapper;
import com.cooksys.social_media.mappers.UserMapper;
import com.cooksys.social_media.repositories.HashtagRepository;
import com.cooksys.social_media.repositories.TweetRepository;
import com.cooksys.social_media.repositories.UserRepository;
import com.cooksys.social_media.services.TweetService;
import com.cooksys.social_media.exceptions.BadRequestException;
import com.cooksys.social_media.exceptions.NotAuthorizedException;
import com.cooksys.social_media.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;
    
    private final UserRepository userRepository;
    private final HashtagRepository hashTagRepository;
    

    private Tweet getTweet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty() || optionalTweet.get().isDeleted()) {
          throw new NotFoundException("Tweet not found with id: " + id);
        }
        return optionalTweet.get();
    }

    private void credentialsCheck(Credentials a, Credentials b) {
        if (!a.equals(b)) {
            throw new NotAuthorizedException("Credentials do not match credentials related to tweet!");
        }
    }


    @Override
    public List<TweetResponseDto> getAllTweets() {
        List<Tweet> descList = tweetRepository.findAllByDeletedFalse();
        Collections.sort(descList, (a,b) -> {
            return b.getPosted().compareTo(a.getPosted());
        });
        return tweetMapper.entitiesToDtos(descList);
    }

    @Override
    public TweetResponseDto getTweetWithId(Long id) {
        
        Tweet t = getTweet(id);
        return tweetMapper.entityToDto(t);
    }

    @Override
    public List<HashtagDto> getAllTagsOnPost(Long id) {
    	
        Tweet t = getTweet(id);
        List<String> emptyList = new ArrayList<>();
        List<String> listOfWords = parseContent(t.getContent(), "#");
        for (String s : listOfWords) {
            emptyList.add(s.substring(1));
        }
        List<Hashtag> listOfTags = getHashTagsFromTokens(emptyList);
        return hashtagMapper.entitiesToDtos(listOfTags);
    }

    @Override
    public List<UserResponseDto> getAllUsersWhoLikeThisTweet(Long id) {
        Tweet t = getTweet(id);
        List<User> usersWhoLikedThisTweet = new ArrayList<>();
        for (User u : t.getLikedByUsers()) {
            if (!u.isDeleted()) {
                usersWhoLikedThisTweet.add(u);
            }
        }
        return userMapper.entitiesToDtos(usersWhoLikedThisTweet);
    }

    @Override
    public ContextDto getContextOfTweet(Long id) {
        Tweet t = getTweet(id);
        ContextDto c = new ContextDto();
        c.setTarget(tweetMapper.entityToDto(t));
        TweetResponseDto trd = c.getTarget();
        List<TweetResponseDto> before = new ArrayList<>();
        List<TweetResponseDto> after = new ArrayList<>();
        while (trd.getInReplyTo() != null) {
            before.add(trd.getInReplyTo());
            trd = trd.getInReplyTo();
        }
        Collections.reverse(before);
        for (TweetResponseDto x : getAllTweets()) {
            if (x.getInReplyTo() == null) continue;
            if ((x.getInReplyTo()).equals(trd)) {
                after.add(x);
                trd = x;
            }
        }
        Collections.reverse(after);
        c.setBefore(before);
        c.setAfter(after);
        return c;
    }

    @Override
    public List<TweetResponseDto> getRepliesToTweet(Long id) {
        Tweet t = getTweet(id);
        List<Tweet> repliesToTweet = new ArrayList<>();
        for (Tweet tR : t.getReplies()) {
            if (!tR.getInReplyTo().isDeleted()) {
                repliesToTweet.add(tR);
            }
        }
        return tweetMapper.entitiesToDtos(repliesToTweet);
    }

    @Override
    public List<TweetResponseDto> getRepostsOfTweet(Long id) {
        Tweet t = getTweet(id);
        List<Tweet> repostsOfTweet = new ArrayList<>();
        for (Tweet tR : t.getReposts()) {
            if (!tR.getRepostOf().isDeleted()) {
                repostsOfTweet.add(tR);
            }
        }
        return tweetMapper.entitiesToDtos(repostsOfTweet);
    }

    @Override
    public List<UserResponseDto> getUsersInTweet(Long id) {
        Tweet t = getTweet(id);
        List<User> mentionedUsers = new ArrayList<>();
        for (User u : t.getMentionedUsers()) {
            if (!u.isDeleted()) {
                mentionedUsers.add(u);
            }
        }
        return userMapper.entitiesToDtos(mentionedUsers);
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getCredentials() == null) {
            throw new BadRequestException("No matching credentials");
        }
        if (tweetRequestDto.getContent() == null) {
            throw new BadRequestException("Must contain content");
        }
        if (tweetRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("Must input password");
        }
        Tweet t = tweetMapper.requestDtoToEntity(tweetRequestDto);
        Optional<User> x = userRepository.findByCredentialsUsernameAndDeletedFalse(tweetRequestDto.getCredentials().getUsername());
        if (x.isEmpty()) {
            throw new NotAuthorizedException("User not found");
        }
        User o = x.get();
        t.setAuthor(o);
        List<String> hTags = parseContent(t.getContent(), "#");
        List<Hashtag> listOfHashtags = getHashTagsFromTokens(hTags);
        for (Hashtag h : listOfHashtags) {
            t.getHashtags().add(h);
        }
        List<String> mentions = parseContent(t.getContent(), "@");
        List<User> listOfMentions = getUsersMentionedFromTokens(mentions);
        for (User u : listOfMentions) {
            t.getMentionedUsers().add(u);
        }
        tweetRepository.saveAndFlush(t);
        return tweetMapper.entityToDto(t);
    }

    @Override
    public TweetResponseDto deleteTweet(CredentialsDto credentialDto, Long id) {
        Tweet tweetToDelete = getTweet(id);
        Credentials c = credentialsMapper.dtoToEntity(credentialDto);
        Credentials t = tweetToDelete.getAuthor().getCredentials();
        credentialsCheck(c, t);
        tweetToDelete.setDeleted(true);
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweetToDelete));
    }


	// HERE
	@Override
	public void likeTweet(CredentialsDto credentialsDto, Long id) {

		// get the tweet to Like
		Optional<Tweet> tweet = tweetRepository.findById(id);

		// get the credentials from request DTO
		Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
		String username = credentials.getUsername();

		// get the user
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

		tweet.ifPresent(likeTweet -> {
			user.ifPresent(userEntity -> {

				// if the user hasn't already liked the tweet
				if (!likeTweet.getLikedByUsers().contains(userEntity)) {
					likeTweet.getLikedByUsers().add(userEntity);
					tweetRepository.saveAndFlush(likeTweet);

					userEntity.getLikedTweets().add(likeTweet);
					userRepository.saveAndFlush(userEntity);
					System.out.println("Tweet was liked");

				}

			});
		});

		if (tweet.isEmpty() || user.isEmpty()) {
			throw new BadRequestException("Could Not Like Tweet");
		}

	}
  

    @Override
    public TweetResponseDto replyToTweet(TweetRequestDto tweetRequestDto, Long id) {
    	
    	Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());
    	Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
    	Tweet reply = tweetMapper.requestDtoToEntity(tweetRequestDto);
    	
    	
    	//If there is a user with the credentials and a tweet with that id exists
    	if (userRepository.existsByCredentials_Username(credentials.getUsername()) && tweet.isPresent()) 
    	{
    		//The tweet has content
    		if(!reply.getContent().isEmpty()) {
    			Tweet target = tweet.get();
    			
    			//set the InReplyTo relationship
    			reply.setInReplyTo(target);
    			
    			//parse for mentions
    			List<String> userNameTokens = parseContent(reply.getContent(),"@");
    			List<User> users = getUsersMentionedFromTokens(userNameTokens);
    			
    			//add the mentioned users to the reply tweet
    			for(User user : users) {
    				reply.getMentionedUsers().add(user);
    			}
    			
    			
    			//parse for tags
    			List<String> hashTagTokens = parseContent(reply.getContent(),"#");
    			List<Hashtag> hashTags = getHashTagsFromTokens(hashTagTokens);
    			
    			//Build the relationship between each tag and the reply 
    			for(Hashtag tag : hashTags) {
    				reply.getHashtags().add(tag);
    				tag.getTweets().add(reply);
    				hashTagRepository.saveAndFlush(tag); //save the tag
    			}
    		}


            reply.setAuthor(userRepository.findByCredentials_Username(credentials.getUsername()));

    		
    	} else {
    		throw new BadRequestException("Could not Reply to tweet");
    	}
    	//save the reply tweet
    	TweetResponseDto response = tweetMapper.entityToDto(tweetRepository.saveAndFlush(reply));
    
    	
        return response;
    }

	// Helper method for replyToTweet
	private List<String> parseContent(String content, String token) {
		List<String> tokens = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(content, " ,.!");

		while (tokenizer.hasMoreElements()) {
			String t = tokenizer.nextToken();
			if (t.startsWith(token)) {
				if (token == "@") {
					tokens.add(t.substring(1).strip());
				}
				if (token == "#") {
					tokens.add(t.strip());
				}
			}

		}

		return tokens;
	}
	// Helper method for replyToTweet
	private List<User> getUsersMentionedFromTokens(List<String> userNames) {
		List<User> mentionedUsers = new ArrayList<User>();
		for (String username : userNames) {
			Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
			if(user.isPresent()) {
				mentionedUsers.add(user.get());
			}

		}
		return mentionedUsers;
	}
	// Helper method for replyToTweet
	private List<Hashtag> getHashTagsFromTokens(List<String> hashTagTokens) {
		List<Hashtag> hashTags = new ArrayList<Hashtag>();
		for (String h : hashTagTokens) {
			//check if hashtag already exists
			Optional<Hashtag> existing = hashTagRepository.findByLabel(h);
			if(existing.isPresent()) {
				hashTags.add(existing.get());
			} else {
				//create  new Hashtag
				Hashtag hashTag = new Hashtag();
				hashTag.setLabel(h);
				
				//save to database and add to list
				hashTags.add(hashTagRepository.saveAndFlush(hashTag));
			}
		}
		return hashTags;
	}
	
    @Override
    public TweetResponseDto repostTweet(CredentialsDto credentialsDto, Long id) {
        Tweet tweetToRepost = getTweet(id);
        Credentials c = credentialsMapper.dtoToEntity(credentialsDto);
        Credentials t = tweetToRepost.getAuthor().getCredentials();
        credentialsCheck(c, t);
        Tweet repostedTweet = new Tweet();
        repostedTweet.setContent(null);
        repostedTweet.setRepostOf(tweetToRepost);
        repostedTweet.setAuthor(tweetToRepost.getAuthor());
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repostedTweet));

    }
}
