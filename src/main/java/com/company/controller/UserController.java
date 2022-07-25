package com.company.controller;

import com.company.dto.payload.UserPayload;
import com.company.dto.response.UserResponse;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> findById(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(userService.findById(email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserPayload userPayload) {
        userService.createUser(userPayload);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@RequestParam(name = "email") String email) {
        userService.deleteUser(email);
    }
}
