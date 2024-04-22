package com.cooba.service.user;

public interface UserService {

    void joinRoom(long userId, long roomId);

    void leaveRoom(long userId);

    void speak(long userId, String message);
}
