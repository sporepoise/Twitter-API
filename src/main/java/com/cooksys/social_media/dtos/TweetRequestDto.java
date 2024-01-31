package com.cooksys.social_media.dtos;

import lombok.*;

@NoArgsConstructor
@Data
public class TweetRequestDto {

	//Fields
	
	private String content;
	
	private CredentialsDto credentials;	
}
