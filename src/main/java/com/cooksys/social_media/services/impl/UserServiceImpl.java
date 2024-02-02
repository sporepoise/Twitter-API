package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.dtos.CredentialsDto;
import com.cooksys.social_media.dtos.TweetResponseDto;
import com.cooksys.social_media.dtos.UserRequestDto;
import com.cooksys.social_media.dtos.UserResponseDto;
import com.cooksys.social_media.entities.Credentials;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;


import com.cooksys.social_media.exceptions.BadRequestException;
import com.cooksys.social_media.exceptions.NotFoundException;
import com.cooksys.social_media.mappers.CredentialsMapper;
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
    private final CredentialsMapper credentialsMapper;


    private final ValidateService validateService;

    private User getExistingUser(String username){
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("Cannot find user with username: " + username);
        }

        return optionalUser.get();
    }

    private void checkCredentials(Credentials firstCredentials, Credentials secondCredentials){
        if(!firstCredentials.equals(secondCredentials)){
            throw new BadRequestException("The username or password provided is incorrect.");
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto getUser(String username) {
        User newUser = getExistingUser(username);
        return userMapper.entityToDto(newUser);
    }

    @Override
    public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
      User user = getExistingUser(username);
      User userToCompare = userMapper.requestDtoToEntity(userRequestDto);
//
//      if(!user.getCredentials().equals(userToCompare.getCredentials())){
//          throw new BadRequestException("The username or password provided is incorrect.");
//      }
        checkCredentials(user.getCredentials(), userToCompare.getCredentials());

      User userToUpdate = userRepository.getById(user.getId());
      userToUpdate.setProfile(userToCompare.getProfile());
      try{
          userRepository.saveAndFlush(userToUpdate);
      }catch(Exception e){
          throw new BadRequestException("Please fill out all required fields.");
        }
      return userMapper.entityToDto(userToUpdate);
    }

    @Override
    public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
        User userToDelete = getExistingUser(username);
//        if(!userToDelete.getCredentials().equals(credentialsMapper.dtoToEntity(credentialsDto))){
//            throw new BadRequestException("The username or password provided is incorrect.");
//        }
        checkCredentials(userToDelete.getCredentials(), credentialsMapper.dtoToEntity(credentialsDto));
        userToDelete = userRepository.getById(userToDelete.getId());
        userToDelete.setDeleted(true);

        return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
    }

    @Override
    public List<Tweet> getUserFeed(String username) {
        User user = getExistingUser(username);
        List<Tweet> feed = new ArrayList<>(tweetRepository.findByAuthorIdAndDeletedFalseOrderByPostedDesc(user.getId()));
        List<User> following = new ArrayList<>(user.getFollowing());

        for(User u: following){
            feed.addAll(tweetRepository.findByAuthorIdAndDeletedFalseOrderByPostedDesc(u.getId()));
        }
       return feed;
    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {

        User user = getExistingUser(username);

        return tweetMapper.entitiesToDtos(tweetRepository.findByAuthorIdAndDeletedFalseOrderByPostedDesc(user.getId()));
    }

    //TODO: retest after creating post tweet method
    @Override
    public List<TweetResponseDto> getUserMentions(String username) {
        User user = getExistingUser(username);
        List<Tweet> mentionedTweets = new ArrayList<>();

        for( Tweet t: tweetRepository.findAllByDeletedFalseOrderByPostedDesc()){
            if(t.getContent() != null && t.getContent().contains("@" + username)){
                mentionedTweets.add(t);
            }
        }


        return tweetMapper.entitiesToDtos(mentionedTweets);
    }


	@Override
	public List<UserResponseDto> getUserFollowers(String username) {
		System.out.println("In the get user followers method");
		System.out.println("The username is " + username);
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		System.out.println(user.isPresent());
		
		
		List<UserResponseDto> followers = new ArrayList<UserResponseDto>();
		
		user.ifPresent(userEntity -> {

			for (User u : userEntity.getFollowers()) {
				
				if (!u.isDeleted()) {
					System.out.println(u.getCredentials().getUsername() + " is a follower of " + userEntity.getCredentials().getUsername());
					followers.add(userMapper.entityToDto(u));
				}
			}

		});

		// user doesn't exist
		if (user.isEmpty()) {
			throw new NotFoundException("User not found.");
		}
		System.out.println("Followers list size is " + followers.size());
		return followers;
	}


	@Override
	public List<UserResponseDto> getUserFolloweringUser(String username) {
		System.out.println("In the  following method");
		System.out.println("username is" + username);
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		List<UserResponseDto> following = new ArrayList<UserResponseDto>();

		user.ifPresent(userEntity -> {
			System.out.println("user is " + userEntity.getCredentials().getUsername());
			System.out.println(userEntity.getFollowing());
			for (User u : userEntity.getFollowing()) {
				System.out.println(u.getCredentials().getUsername());
				if (!u.isDeleted()) {
					following.add(userMapper.entityToDto(u));
				}
			}

		});

		// user doesn't exist
		if (user.isEmpty()) {
			throw new NotFoundException("User not found.");
		}
		System.out.println("Following list size is " + following.size());
		return following;
	}

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User newUser = userMapper.requestDtoToEntity(userRequestDto);
        if(newUser.getCredentials() == null){
            throw new BadRequestException("Please fill out all required fields.");
        }
        if(!validateService.isUsernameAvailable(newUser.getCredentials().getUsername())){
            throw new BadRequestException("Username already exists");
        }
        try{
            userRepository.saveAndFlush(newUser);
        } catch(Exception e){
            throw new BadRequestException("Please fill out all required fields.");
        }
        if(validateService.doesUsernameExist(newUser.getCredentials().getUsername())){
            newUser.setDeleted(false);
            try{
                userRepository.saveAndFlush(newUser);
            } catch (Exception e){
                throw new BadRequestException("Please fill out all required fields.");
            }
            return userMapper.entityToDto(newUser);
        }


       return userMapper.entityToDto(newUser);

    }

    @Override
    public void followUser(String username, CredentialsDto credentialsDto) {
        User userToFollow = getExistingUser(username);
        User user = getExistingUser(credentialsDto.getUsername());

//        if(!user.getCredentials().equals(credentialsMapper.dtoToEntity(credentialsDto))){
//            throw new BadRequestException("The username or password provided is incorrect.");
//        }

        checkCredentials(user.getCredentials(), credentialsMapper.dtoToEntity(credentialsDto));

        List<User> following = user.getFollowing();
        for(User u: following){
            if(u.getCredentials().getUsername().equals(username)){
                throw new BadRequestException("You are already following this user.");
            }
        }

        following.add(userToFollow);
        userRepository.saveAndFlush(user);
        List<User> followers = userToFollow.getFollowers();
        followers.add(user);
        userRepository.saveAndFlush(userToFollow);

    }

    @Override
    public void unfollowUser(String username, CredentialsDto credentialsDto) {
        User userToUnfollow = getExistingUser(username);
        User user = getExistingUser(credentialsDto.getUsername());

//        if(!user.getCredentials().equals(credentialsMapper.dtoToEntity(credentialsDto))){
//            throw new BadRequestException("The username or password provided is incorrect.");
//        }
        checkCredentials(user.getCredentials(), credentialsMapper.dtoToEntity(credentialsDto));

        List<User> following = user.getFollowing();
        for(User u: following){
            if(u.getCredentials().getUsername().equals(username)){
                throw new BadRequestException("You don't currently follow this user.");
            }
        }

        following.remove(userToUnfollow);
        userRepository.saveAndFlush(user);
        List<User> followers = userToUnfollow.getFollowers();
        followers.remove(user);
        userRepository.saveAndFlush(userToUnfollow);
    }

}
