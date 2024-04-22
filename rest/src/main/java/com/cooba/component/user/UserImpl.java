package com.cooba.component.user;

import com.cooba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserImpl implements User {
    private final UserRepository userRepository;

    @Override
    public void create(String name) {

    }

    @Override
    public void delete(long userId) {

    }

    @Override
    public void joinRoom(long userId, long roomId) {

    }

    @Override
    public void leaveRoom(long userId) {

    }

    @Override
    public void speak(long userId, String message) {

    }

    @Override
    public void getCurrentRoom(long userId) {

    }
}
