package com.cooba.controller;

import com.cooba.component.user.User;
import com.cooba.request.CreateUserRequest;
import com.cooba.request.RoomEnterRequest;
import com.cooba.request.RoomLeaveRequest;
import com.cooba.request.SpeakRequest;
import com.cooba.service.user.UserService;
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
    private final UserService userService;

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

    @PostMapping("/room/enter")
    public ResponseEntity<?> enterRoom(@Valid @RequestBody RoomEnterRequest request) {
        userService.enterRoom(request.getUserId(), request.getRoomId());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/room/leave")
    public ResponseEntity<?> leaveRoom(@Valid @RequestBody RoomLeaveRequest request) {
        userService.leaveRoom(request.getUserId());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/speak")
    public ResponseEntity<?> speak(@Valid @RequestBody SpeakRequest request) {
        userService.speak(request.getUserId(), request.getMessage());
        return ResponseEntity.ok(true);
    }
}
