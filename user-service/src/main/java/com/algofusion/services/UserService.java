package com.algofusion.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algofusion.common.dto.GlobalException;
import com.algofusion.entities.User;
import com.algofusion.mappers.UserMapper;
import com.algofusion.repositories.UserRepository;
import com.algofusion.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    public String saveUser(UserRequest userRequest, Map<String, Object> informations) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new GlobalException("Email already exists!");
        }

        User user = userMapper.toUser(userRequest);
        try {
            objectMapper.updateValue(user, informations);
            userRepository.save(user);
            return "The user created successfully";
        } catch (Exception e) {
            throw new GlobalException("Failed to map" + e);
        }
    }

    public void updateUser(String email, Map<String, Object> informations) {
        User byEmail = findByEmail(email);

        try {
            objectMapper.updateValue(byEmail, informations);
            userRepository.save(byEmail);
        } catch (Exception e) {
            throw new GlobalException("Failed to map" + e);
        }
    }

    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException("User not found by email: " + email));
    }

    public User findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new GlobalException("User not found by refresh token: " + refreshToken));
    }
}
