package com.cooksys.social_media.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.social_media.entities.Tweet;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByAuthorIdAndDeletedFalseOrderByPostedDesc(Long userId);

    List<Tweet> findAllByDeletedFalseOrderByPostedDesc();

}
