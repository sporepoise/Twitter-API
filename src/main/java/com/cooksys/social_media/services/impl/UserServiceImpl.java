package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.dtos.UserRequestDto;
import com.cooksys.social_media.dtos.UserResponseDto;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;
import com.cooksys.social_media.exceptions.NotFoundException;
import com.cooksys.social_media.mappers.UserMapper;
import com.cooksys.social_media.repositories.UserRepository;
import com.cooksys.social_media.services.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

    @Override
    public UserResponseDto getUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
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
    public List<Tweet> getUserTweets(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserTweets'");
    }

    @Override
    public List<Tweet> getUserMentions(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserMentions'");
    }

    //not done
	@Override
	public List<UserResponseDto> getUserFollowers(String username) {
		Optional<User> user = userRepository.findByCredentials_Username(username);
		List<UserResponseDto> followers = new ArrayList<UserResponseDto>();

		user.ifPresent(userEntity -> {

			if (!userEntity.isDeleted()) {
				for (User u : userEntity.getFollowers()) {
					if (!u.isDeleted()) {
						followers.add(userMapper.entityToDto(u));
					}
				}
			} else {
				//throw new NotFoundException("User not found");
			}
		});

		//User can be deleted 
		
		// user doesn't exist
		if (user.isEmpty()) {
			throw new NotFoundException("User not found.");
		}
		return followers;
	}

	//not done
    @Override
    public List<UserResponseDto> getUserFolloweringUser(String username) {
    	Optional<User> user = userRepository.findByCredentials_Username(username);
		List<UserResponseDto> following = new ArrayList<UserResponseDto>();
       return null;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
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
