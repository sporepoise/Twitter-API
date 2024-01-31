package com.cooksys.social_media.services;


import java.util.List;

import com.cooksys.social_media.dtos.UserRequestDto;
import com.cooksys.social_media.dtos.UserResponseDto;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;

public interface UserService {

    List<User> getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUser(String username);

    UserResponseDto updateUser(String username);

    UserResponseDto deleteUser(String username);

    void followUser(String username, UserRequestDto userRequestDto);
    
    void unfollowUser(String username, UserRequestDto userRequestDto);

    List<Tweet> getUserFeed(String username);

    List<Tweet> getUserTweets(String username);

    List<Tweet> getUserMentions(String username);

    List<User> getUserFollowers(String username);

    List<User> getUserFolloweringUser(String username);

}
