package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.dtos.TweetRequestDto;
import com.cooksys.social_media.dtos.TweetResponseDto;
import com.cooksys.social_media.entities.Credentials;
import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;
import com.cooksys.social_media.exceptions.BadRequestException;
import com.cooksys.social_media.mappers.CredentialsMapper;
import com.cooksys.social_media.mappers.TweetMapper;
import com.cooksys.social_media.repositories.TweetRepository;
import com.cooksys.social_media.repositories.UserRepository;
import com.cooksys.social_media.services.TweetService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
	
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	
	
	private final TweetMapper tweetMapper;
	private final CredentialsMapper credentialsMapper;

	
    @Override
    public List<Tweet> getAllTweets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTweets'");
    }

    @Override
    public TweetResponseDto getTweetWithId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTweetWithId'");
    }

    @Override
    public List<Hashtag> getAllTagsOnPost(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTagsOnPost'");
    }

    @Override
    public List<User> getAllUsersWhoLikeThisTweet(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsersWhoLikeThisTweet'");
    }

    @Override
    public TweetResponseDto getContextOfTweet(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getContextOfTweet'");
    }

    @Override
    public List<Tweet> getRepliesToTweet(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRepliesToTweet'");
    }

    @Override
    public List<Tweet> getRepostsOfTweet(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRepostsOfTweet'");
    }

    @Override
    public List<User> getUsersInTweet(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsersInTweet'");
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTweet'");
    }

    @Override
    public TweetResponseDto deleteTweet(TweetRequestDto tweetRequestDto, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTweet'");
    }

	// HERE
	@Override
	public void likeTweet(TweetRequestDto tweetRequestDto, Long id) {
		// get the tweet to Like
		Optional<Tweet> tweet = tweetRepository.findById(id);

		// get the credentials from request DTO
		Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());
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

	}

    @Override
    public TweetResponseDto replyToTweet(TweetRequestDto tweetRequestDto, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replyToTweet'");
    }

    @Override
    public TweetResponseDto repostTweet(TweetRequestDto tweetRequestDto, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'repostTweet'");
    }
}
