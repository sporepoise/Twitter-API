package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;
import com.cooksys.social_media.dtos.TweetRequestDto;
import com.cooksys.social_media.dtos.TweetResponseDto;

import com.cooksys.social_media.services.TweetService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    @Override
    public List<Tweet> getAllTweets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTweets'");
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTweet'");
    }

    @Override
    public TweetResponseDto getTweetWithId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTweetWithId'");
    }

    @Override
    public TweetResponseDto deleteTweet(TweetRequestDto tweetRequestDto, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTweet'");
    }

    @Override
    public void likeTweet(TweetRequestDto tweetRequestDto, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'likeTweet'");
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

    //remember to use keyword final when declaring fields
}
