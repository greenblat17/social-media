package com.greenblat.socialmedia.controller;

import com.greenblat.socialmedia.controller.documentation.AuthDocumentation;
import com.greenblat.socialmedia.dto.auth.AuthRequest;
import com.greenblat.socialmedia.dto.auth.AuthResponse;
import com.greenblat.socialmedia.dto.auth.RegisterRequest;
import com.greenblat.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthDocumentation {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Validated RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Validated AuthRequest registerRequest) {
        return ResponseEntity.ok(authService.authenticateUser(registerRequest));
    }
}
