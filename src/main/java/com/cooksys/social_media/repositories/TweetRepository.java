package com.cooksys.social_media.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.social_media.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
