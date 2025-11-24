package com.lesley.GestionPresences.auth.service;

import com.lesley.GestionPresences.Enum.Role;
import com.lesley.GestionPresences.auth.entities.RefreshToken;
import com.lesley.GestionPresences.auth.utils.AuthResponse;
import com.lesley.GestionPresences.auth.utils.LoginRequest;
import com.lesley.GestionPresences.auth.utils.RegisterRequest;
import com.lesley.GestionPresences.entities.User;
import com.lesley.GestionPresences.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTservice jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTservice jwtService, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse registerUser(RegisterRequest registerRequest , Role role) {
        var user = User.builder()
                .nom(registerRequest.getName())
                .email(registerRequest.getEmail())
                .motDePasse(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();

        User userSaved = userRepository.save(user);
        var accessToken = jwtService.generateToken(userSaved);
        var refreshToken = refreshTokenService.createRefreshToken(userSaved.getEmail());

        return  AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        var  accessToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }





}
