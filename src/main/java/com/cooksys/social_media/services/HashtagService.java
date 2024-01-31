package com.cooksys.social_media.services;

import java.util.List;

import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;

public interface HashtagService {

    List<Hashtag> getAllHashtags();

    List<Tweet> getAllTweetsWithHashtag();

}
