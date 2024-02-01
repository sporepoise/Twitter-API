package com.cooksys.social_media.services;

import java.util.List;

import com.cooksys.social_media.dtos.ContextDto;
import com.cooksys.social_media.dtos.CredentialsDto;
import com.cooksys.social_media.dtos.TweetRequestDto;
import com.cooksys.social_media.dtos.TweetResponseDto;
import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;


public interface TweetService {

    List<TweetResponseDto> getAllTweets();

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto getTweetWithId(Long id);

    TweetResponseDto deleteTweet(CredentialsDto credentialsDto, Long id);

    void likeTweet(CredentialsDto credentialsDto, Long id);

    TweetResponseDto replyToTweet(CredentialsDto credentialsDto, Long id);

    TweetResponseDto repostTweet(CredentialsDto credentialsDto, Long id);

    List<Hashtag> getAllTagsOnPost(Long id);

    List<User> getAllUsersWhoLikeThisTweet(Long id);

    ContextDto getContextOfTweet(Long id);

    List<Tweet> getRepliesToTweet(Long id);

    List<Tweet> getRepostsOfTweet(Long id);

    List<User> getUsersInTweet(Long id);

}
