package com.greenblat.socialmedia.service;

import com.greenblat.socialmedia.dto.auth.AuthRequest;
import com.greenblat.socialmedia.dto.auth.AuthResponse;
import com.greenblat.socialmedia.dto.auth.RegisterRequest;
import com.greenblat.socialmedia.exception.ResourceNotFoundException;
import com.greenblat.socialmedia.mapper.UserMapper;
import com.greenblat.socialmedia.model.User;
import com.greenblat.socialmedia.repository.UserRepository;
import com.greenblat.socialmedia.security.JwtService;
import com.greenblat.socialmedia.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registerUser(RegisterRequest request) {
        var user = userMapper.mapToUser(request, passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return buildAuthResponseWithToken(user);
    }

    public AuthResponse authenticateUser(AuthRequest request) {
        var authInputToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );
        authenticationManager.authenticate(authInputToken);
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with username [%s] not found"
                                        .formatted(request.username())
                        ));
        return buildAuthResponseWithToken(user);
    }

    private AuthResponse buildAuthResponseWithToken(User user) {
        var userDetails = new UserDetailsImpl(user);
        var jwtToken =jwtService.generateToken(userDetails);
        return new AuthResponse(jwtToken);
    }
}
