package com.algofusion.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algofusion.common.services.security.JwtUtil;
import com.algofusion.common.services.security.RedisTokenService;
import com.algofusion.entities.User;
import com.algofusion.request.UserRequest;
import com.algofusion.response.TokensResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisTokenService redisTokenService;

    @Value("${refresh-token.expiration}")
    long expiration;

    public String signup(UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        String refreshToken = jwtUtil.generateRefreshToken();
        Map<String, Object> refMap = new HashMap<>();
        refMap.put("refreshToken", refreshToken);
        refMap.put("refreshTokenExpiresAt", Instant.now().plus(expiration, ChronoUnit.DAYS));
        return userService.saveUser(userRequest, refMap);
    }

    public TokensResponse login(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));

        User byEmail = userService.findByEmail(userRequest.getEmail());

        String token = generateToken(byEmail);

        String refreshToken = null;

        if (!jwtUtil.validateRefreshTokenWithDate(byEmail.getRefreshTokenExpiresAt())) {
            refreshToken = jwtUtil.generateRefreshToken();

            Map<String, Object> refMap = new HashMap<>();
            refMap.put("refreshToken", refreshToken);
            refMap.put("refreshTokenExpiresAt", Instant.now().plus(expiration, ChronoUnit.DAYS));

            userService.updateUser(userRequest.getEmail(), refMap);
        } else {
            refreshToken = byEmail.getRefreshToken();
        }

        return TokensResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        String token = jwtUtil.generateToken(claims, user.getEmail());
        redisTokenService.storeToken(user.getEmail(), token);

        return token;
    }

    public boolean validateRefreshToken(String refreshTokenReq) {
        User byRefreshToken = userService.findByRefreshToken(refreshTokenReq);

        return jwtUtil.validateRefreshTokenWithDate(byRefreshToken.getRefreshTokenExpiresAt());
    }

    public String generateTokenFromRefreshToken(String refreshTokenReq) {
        User byRefreshToken = userService.findByRefreshToken(refreshTokenReq);

        return generateToken(byRefreshToken);
    }
}
