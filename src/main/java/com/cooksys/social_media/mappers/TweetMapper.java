package com.cooksys.social_media.mappers;

import com.cooksys.social_media.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TweetMapper {

    TweetResponseDto entityToDto(Tweet tweet);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);

    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);
}
