package com.cooksys.social_media.services.impl;

import com.cooksys.social_media.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {@Override
    public boolean doesTagExist(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doesTagExist'");
    }

    @Override
    public boolean doesUsernameExist(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doesUsernameExist'");
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUsernameAvailable'");
    }
}
