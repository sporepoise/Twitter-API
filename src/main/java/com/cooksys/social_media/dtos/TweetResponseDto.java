package com.cooksys.social_media.dtos;



import java.sql.Timestamp;

import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;

import lombok.*;

@NoArgsConstructor
@Data
public class TweetResponseDto {

	
	/*
	 * A tweet posted by a user. The posted timestamp should be assigned when the
	 * tweet is first created, and must not be updated.
	 * 
	 * There are three distinct variations of tweets: simple, repost, and reply.
	 * 
	 * A simple tweet has a content value but no inReplyTo or repostOf values A
	 * repost has a repostOf value but no content or inReplyTo values A reply has
	 * content and inReplyTo values, but no repostOf value
	 * 
	 */
	 
	
	//Fields
	private int id;
	
	private User author;
	
	private Timestamp posted;
	
	private String content;
	
	private Tweet inReplyTo;
	
	private Tweet repostOf;
}
