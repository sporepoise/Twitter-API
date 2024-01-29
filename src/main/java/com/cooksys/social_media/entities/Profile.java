package com.cooksys.social_media.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Data
public class Profile {

    private String firstName;

    private String lastName;

    @NonNull
    private String email;

    private String phone;
}
