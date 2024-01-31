package com.cooksys.social_media.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {

	/*
	 * The before property represents the chain of replies
	 * that led to the target tweet, and the after property
	 * represents the chain of replies that followed the 
	 * target tweet.
	 */
	
	
	private TweetResponseDto target;
    private List<TweetResponseDto> before;
    private List<TweetResponseDto> after;
}
