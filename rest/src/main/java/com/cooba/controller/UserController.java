package com.cooba.controller;

import com.cooba.component.user.User;
import com.cooba.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final User user;

    @PostMapping("/create")
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequest request) {
        Long userId = user.create(request.getName());
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String userId) {
        boolean deleted = user.delete(Long.parseLong(userId));
        return ResponseEntity.ok(deleted);
    }
}
