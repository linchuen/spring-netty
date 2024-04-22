package com.cooba.component.user;

import com.cooba.entity.UserEntity;

public interface User {
    Long create(String name);

    boolean delete(long userId);

    UserEntity verify(long userId);

    void joinRoom(long userId, long roomId);

    void leaveRoom(long userId);

    void speak(long userId, String message);

    Long getCurrentRoomId(long userId);
}
