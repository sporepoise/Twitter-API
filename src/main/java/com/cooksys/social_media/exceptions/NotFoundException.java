package com.cooksys.social_media.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {

//TODO: create methods in controller advice with matching @exceptionhandler class per exception

    private String message;
}
