package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.dtos.TweetResponseDto;
import com.cooksys.social_media.dtos.UserRequestDto;
import com.cooksys.social_media.dtos.UserResponseDto;
import com.cooksys.social_media.entities.Credentials;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;

import com.cooksys.social_media.exceptions.BadRequestException;
import com.cooksys.social_media.exceptions.NotFoundException;
import com.cooksys.social_media.mappers.TweetMapper;
import com.cooksys.social_media.mappers.UserMapper;
import com.cooksys.social_media.repositories.TweetRepository;
import com.cooksys.social_media.repositories.UserRepository;
import com.cooksys.social_media.services.UserService;
import com.cooksys.social_media.services.ValidateService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;


    private final ValidateService validateService;
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto getUser(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("Cannot find user with username: " + username);
        }
        return userMapper.entityToDto(optionalUser.get());
    }

    @Override
    public UserResponseDto updateUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public UserResponseDto deleteUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public List<Tweet> getUserFeed(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserFeed'");
    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("Cannot find user with username: " + username);
        }

        User user = optionalUser.get();

        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthorIdAndDeletedFalseOrderByPostedDesc(user.getId()));
    }

    //TODO: retest after creating post tweet method
    @Override
    public List<TweetResponseDto> getUserMentions(String username) {
        //TODO: create separate method for this block of code
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("Cannot find user with username: " + username);
        }

        List<Tweet> mentionedTweets = new ArrayList<>();

        for( Tweet t: tweetRepository.findAllByDeletedFalseOrderByPostedDesc()){
            if(t.getContent() != null && t.getContent().contains("@" + username)){
                mentionedTweets.add(t);
            }
        }

        return tweetMapper.entitiesToDtos(mentionedTweets);
    }

    @Override
    public List<User> getUserFollowers(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserFollowers'");
    }

    @Override
    public List<User> getUserFolloweringUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserFolloweringUser'");
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User newUser = userMapper.requestDtoToEntity(userRequestDto);
//        TODO: test after pulling validateService methods
//        if(validateService.doesUsernameExist(newUser.getCredentials().getUsername())){
//            newUser.setDeleted(false);
//            return userMapper.entityToDto(newUser);
//        }
//
//        if(!validateService.isUsernameAvailable(newUser.getCredentials().getUsername())){
//            throw new BadRequestException("Username already exists");
//        }
        try{
            userRepository.saveAndFlush(newUser);
        } catch(Exception e){
            throw new BadRequestException("Please fill out all required fields.");
        }
       return userMapper.entityToDto(newUser);

    }

    @Override
    public void followUser(String username, UserRequestDto userRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'followUser'");
    }

    @Override
    public void unfollowUser(String username, UserRequestDto userRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unfollowUser'");
    }

}
