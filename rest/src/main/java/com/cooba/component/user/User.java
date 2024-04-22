package com.cooba.component.user;

public interface User {
    void create(String name);

    void delete(long userId);

    void joinRoom(long userId, long roomId);

    void leaveRoom(long userId);

    void speak(long userId, String message);

    Long getCurrentRoom(long userId);
}
