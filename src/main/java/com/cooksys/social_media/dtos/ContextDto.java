package com.cooksys.social_media.dtos;

import com.cooksys.social_media.entities.Tweet;

import lombok.*;

@NoArgsConstructor
@Data
public class ContextDto {

	/*
	 * The before property represents the chain of replies
	 * that led to the target tweet, and the after property
	 * represents the chain of replies that followed the 
	 * target tweet.
	 */
	
	
	private Tweet before; 
	private Tweet target;
	private Tweet after;
}
