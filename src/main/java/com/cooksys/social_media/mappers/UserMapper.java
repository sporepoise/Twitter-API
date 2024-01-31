package com.cooksys.social_media.mappers;

import com.cooksys.social_media.dtos.UserRequestDto;
import com.cooksys.social_media.dtos.UserResponseDto;
import com.cooksys.social_media.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {

    UserResponseDto entityToDto(User user);

    List<UserResponseDto> entitiesToDtos(List<User> users);

    User requestDtoToEntity(UserRequestDto userRequestDto);

}