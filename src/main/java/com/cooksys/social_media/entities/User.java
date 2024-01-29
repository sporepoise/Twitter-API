package com.cooksys.social_media.entities;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="user_table")
public class User {

    @GeneratedValue
    @Id
    private Long id;

    private String username;

    private String password;

    private Timestamp joined;

    private boolean deleted;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    @Embedded
    private Profile profile;

    @Embedded
    private Credentials credentials;

}
