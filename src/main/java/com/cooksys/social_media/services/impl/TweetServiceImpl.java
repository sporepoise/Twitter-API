package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.dtos.ContextDto;
import com.cooksys.social_media.dtos.CredentialsDto;
import com.cooksys.social_media.dtos.TweetRequestDto;
import com.cooksys.social_media.dtos.TweetResponseDto;
import com.cooksys.social_media.entities.Credentials;
import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;

import com.cooksys.social_media.mappers.CredentialsMapper;
import com.cooksys.social_media.mappers.TweetMapper;
import com.cooksys.social_media.repositories.TweetRepository;

import com.cooksys.social_media.services.TweetService;
import com.cooksys.social_media.exceptions.NotAuthorizedException;
import com.cooksys.social_media.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final CredentialsMapper credentialsMapper;


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
    public List<Hashtag> getAllTagsOnPost(Long id) {
        Tweet t = getTweet(id);
        return t.getHashtags();
    }

    @Override
    public List<User> getAllUsersWhoLikeThisTweet(Long id) {
        Tweet t = getTweet(id);
        List<User> usersWhoLikedThisTweet = new ArrayList<>();
        for (User u : t.getLikedByUsers()) {
            if (!u.isDeleted()) {
                usersWhoLikedThisTweet.add(u);
            }
        }
        return usersWhoLikedThisTweet;
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
    public List<Tweet> getRepliesToTweet(Long id) {
        Tweet t = getTweet(id);
        List<Tweet> repliesToTweet = new ArrayList<>();
        for (Tweet tR : t.getReposts()) {
            if (!tR.getInReplyTo().isDeleted()) {
                repliesToTweet.add(tR);
            }
        }
        return repliesToTweet;
    }

    @Override
    public List<Tweet> getRepostsOfTweet(Long id) {
        Tweet t = getTweet(id);
        List<Tweet> repostsOfTweet = new ArrayList<>();
        for (Tweet tR : t.getReposts()) {
            if (!tR.getRepostOf().isDeleted()) {
                repostsOfTweet.add(tR);
            }
        }
        return repostsOfTweet;
    }

    @Override
    public List<User> getUsersInTweet(Long id) {
        Tweet t = getTweet(id);
        List<User> mentionedUsers = new ArrayList<>();
        for (User u : t.getMentionedUsers()) {
            if (!u.isDeleted()) {
                mentionedUsers.add(u);
            }
        }
        return mentionedUsers;
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTweet'");
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
		/*
		// get the tweet to Like
		Optional<Tweet> tweet = tweetRepository.findById(id);

		// get the credentials from request DTO
		//Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());
		String username = credentials.getUsername();

		// get the user
		Optional<User> user = userRepository.findByCredentials_Username(username);

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
	*/
	}
  

    @Override
    public TweetResponseDto replyToTweet(CredentialsDto credentialsDto, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replyToTweet'");
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
