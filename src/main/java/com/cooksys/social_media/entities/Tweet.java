package com.cooksys.social_media.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Tweet {

    @GeneratedValue
    @Id
    private Long id;

    private Integer author;

    private Timestamp posted;

    private boolean deleted;

    private String content;

    private Integer inReplyTo;

    private Integer repostOf;

    @ManyToOne
    @JoinColumn(name = "author")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags;

    @ManyToMany(mappedBy = "tweetList")
    private List<User> users;

    @ManyToMany(mappedBy = "tweetsList")
    private List<User> userList;
}
