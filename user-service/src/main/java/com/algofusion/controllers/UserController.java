package com.algofusion.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algofusion.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.existsUserById(id));
    }
}
