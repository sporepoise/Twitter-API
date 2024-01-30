package com.cooksys.social_media.mappers;

import com.cooksys.social_media.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

//TODO: find out if I need to include other mappers in 'uses' argument
@Mapper(componentModel = "spring", uses = {HashtagMapper.class})
public interface TweetMapper {

    TweetResponseDto entityToDto(Tweet tweet);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);

    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);
}
