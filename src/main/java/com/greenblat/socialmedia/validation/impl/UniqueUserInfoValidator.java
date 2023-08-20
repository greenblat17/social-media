package com.greenblat.socialmedia.validation.impl;

import com.greenblat.socialmedia.dto.auth.RegisterRequest;
import com.greenblat.socialmedia.repository.UserRepository;
import com.greenblat.socialmedia.validation.UniqueUserInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UniqueUserInfoValidator implements ConstraintValidator<UniqueUserInfo, RegisterRequest> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmail(request.email())
               && !userRepository.existsByUsername(request.username());
    }
}
