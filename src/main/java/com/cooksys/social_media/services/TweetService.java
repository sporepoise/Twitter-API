package com.cooksys.social_media.services;

import java.util.List;

import com.cooksys.social_media.dtos.TweetRequestDto;
import com.cooksys.social_media.dtos.TweetResponseDto;
import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;

public interface TweetService {

    List<Tweet> getAllTweets();

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto getTweetWithId(Long id);

    TweetResponseDto deleteTweet(TweetRequestDto tweetRequestDto, Long id);

    void likeTweet(TweetRequestDto tweetRequestDto, Long id);

    TweetResponseDto replyToTweet(TweetRequestDto tweetRequestDto, Long id);

    TweetResponseDto repostTweet(TweetRequestDto tweetRequestDto, Long id);

    List<Hashtag> getAllTagsOnPost(Long id);

    List<User> getAllUsersWhoLikeThisTweet(Long id);

    TweetResponseDto getContextOfTweet(Long id);

    List<Tweet> getRepliesToTweet(Long id);

    List<Tweet> getRepostsOfTweet(Long id);

    List<User> getUsersInTweet(Long id);


}
