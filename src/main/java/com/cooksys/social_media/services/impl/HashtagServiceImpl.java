package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.dtos.HashtagDto;

import com.cooksys.social_media.mappers.HashtagMapper;

import com.cooksys.social_media.repositories.HashtagRepository;

import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.services.HashtagService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagMapper hashTagMapper;
	private final HashtagRepository hashTagRepository;
	
    @Override
    public List<HashtagDto> getAllHashtags() {
        // TODO Auto-generated method stub
    	return hashTagMapper.entitiesToDtos(hashTagRepository.findAll());
        
    }

    @Override
    public List<Tweet> getAllTweetsWithHashtag() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTweetsWithHashtag'");
    }
}
