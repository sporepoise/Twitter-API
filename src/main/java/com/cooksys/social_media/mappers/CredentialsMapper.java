package com.cooksys.social_media.mappers;

import com.cooksys.social_media.entities.Credentials;
import com.cooksys.social_media.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface CredentialsMapper {
   //TODO: where to add - @Mapping(target = "username", source = "credentials.username") ?
    CredentialsDto entityToDto(Credentials credentials);

   // List<CredentialsResponseDto> entitiesToDtos(List<Credentials> credentials);

    Credentials dtoToEntity(CredentialsDto credentialsDto);
}
