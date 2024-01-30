package com.cooksys.social_media.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Data
public class Credentials {

    @NonNull
    private String username;

    @NonNull
    private String password;
}
