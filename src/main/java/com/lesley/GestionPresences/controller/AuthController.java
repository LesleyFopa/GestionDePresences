package com.lesley.GestionPresences.controller;

import com.lesley.GestionPresences.Enum.Role;
import com.lesley.GestionPresences.auth.entities.RefreshToken;
import com.lesley.GestionPresences.auth.service.AuthService;
import com.lesley.GestionPresences.auth.service.JWTservice;
import com.lesley.GestionPresences.auth.service.RefreshTokenService;
import com.lesley.GestionPresences.auth.utils.AuthResponse;
import com.lesley.GestionPresences.auth.utils.LoginRequest;
import com.lesley.GestionPresences.auth.utils.RefreshTokenRequest;
import com.lesley.GestionPresences.auth.utils.RegisterRequest;
import com.lesley.GestionPresences.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JWTservice jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JWTservice jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register/{role}")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest ,@PathVariable Role role) {
        return ResponseEntity.ok(authService.registerUser(registerRequest,role));
    }

    @PostMapping
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}
