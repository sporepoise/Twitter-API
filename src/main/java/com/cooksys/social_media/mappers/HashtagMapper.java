package com.cooksys.social_media.mappers;

import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface HashtagMapper {

    HashtagResponseDto entityToDto(Hashtag hashtag);

    List<HashtagResponseDto> entitiesToDtos(List<Hashtag> hashtags);

    Hashtag requestDtoToEntity(HashtagRequestDto hashtagRequestDto);
}
