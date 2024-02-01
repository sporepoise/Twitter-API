package com.cooksys.social_media.controllers;

import java.util.List;

import com.cooksys.social_media.dtos.ContextDto;
import com.cooksys.social_media.dtos.CredentialsDto;
import com.cooksys.social_media.dtos.TweetRequestDto;
import com.cooksys.social_media.dtos.TweetResponseDto;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.social_media.entities.Hashtag;
import com.cooksys.social_media.entities.Tweet;
import com.cooksys.social_media.entities.User;
import com.cooksys.social_media.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetWithId(@PathVariable Long id) {
        return tweetService.getTweetWithId(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
        return tweetService.deleteTweet(credentialsDto, id);
    }

    @PostMapping("/{id}/like")
    public void likeTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
        tweetService.likeTweet(credentialsDto, id);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto replyToTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
        return tweetService.replyToTweet(credentialsDto, id);
    }

    @PostMapping("/{id}/repost")
    public TweetResponseDto repostTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
        return tweetService.repostTweet(credentialsDto, id);
    }

    @GetMapping("/{id}/tags")
    public List<Hashtag> getAllTagsOnPost(@PathVariable Long id) {
        return tweetService.getAllTagsOnPost(id);
    }

    @GetMapping("/{id}/likes")
    public List<User> getAllUsersWhoLikeThisTweet(@PathVariable Long id) {
        return tweetService.getAllUsersWhoLikeThisTweet(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getContextOfTweet(@PathVariable Long id) {
        return tweetService.getContextOfTweet(id);
    }

    @GetMapping("/{id}/replies")
    public List<Tweet> getRepliesToTweet(@PathVariable Long id) {
        return tweetService.getRepliesToTweet(id);
    }

    @GetMapping("/{id}/reposts")
    public List<Tweet> getRepostsOfTweet(@PathVariable Long id) {
        return tweetService.getRepostsOfTweet(id);
    }

    @GetMapping("/{id}/mentions")
    public List<User> getUsersInTweet(@PathVariable Long id) {
        return tweetService.getUsersInTweet(id);
    }

}
