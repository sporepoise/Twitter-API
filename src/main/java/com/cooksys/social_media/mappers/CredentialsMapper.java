package com.cooksys.social_media.mappers;

import com.cooksys.social_media.entities.Credentials;
import com.cooksys.social_media.entities.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDto entityToDto(Credentials credentials);

   // List<CredentialsResponseDto> entitiesToDtos(List<Credentials> credentials);

    Credentials dtoToEntity(CredentialsDto credentialsDto);
}
