package com.cooksys.social_media.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    public List<Hashtag> getAllHashtags() {
        return hashtagService.getAllHashtags();
    }

    @GetMapping("/{label}")
    public List<Tweet> getAllTweetsWithHashtag(@PathVariable String label) {
        return hashtagService.getAllTweetsWithHashtag();
    }

    

}
