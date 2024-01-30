package com.cooksys.social_media.dtos;
import java.sql.Timestamp;

import lombok.*;


@NoArgsConstructor
@Data
public class UserResponseDto {

	private String username; // should be unique
	private ProfileDto profile; 
	private Timestamp joined; // asigned when user is created
}
