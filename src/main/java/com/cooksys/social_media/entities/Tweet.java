package com.cooksys.social_media.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

}
