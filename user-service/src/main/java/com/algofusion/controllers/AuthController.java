package com.algofusion.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algofusion.helper.CookieHelper;
import com.algofusion.request.UserRequest;
import com.algofusion.response.TokensResponse;
import com.algofusion.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${refresh-token.expiration}")
    long expiration;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserRequest userRequest, HttpServletResponse response) {
        TokensResponse login = authService.login(userRequest);

        ResponseCookie refreshCookie = CookieHelper.generateCookie("refreshToken", login.getRefreshToken(), expiration);
        response.setHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok().body(login.getAccessToken());
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found");
        }

        if (!authService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        return ResponseEntity.ok(authService.generateTokenFromRefreshToken(refreshToken));
    }

}
