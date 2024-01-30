package com.cooksys.social_media.dtos;
import java.sql.Timestamp;

import lombok.*;

@NoArgsConstructor
@Data
public class HashTagDto {
	
	private String label; // must be unique

	private Timestamp firstUsed; // assigned on creation

	
	
	// updated every time a new tweet is tagged with the hashtag
	private Timestamp lastUsed;
	

}
