package com.cooksys.social_media.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Data
public class Credentials {

    private String username;

    private String password;
}
