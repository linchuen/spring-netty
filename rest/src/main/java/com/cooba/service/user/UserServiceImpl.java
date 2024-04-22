package com.cooba.service.user;

import com.cooba.component.chatroom.ChatRoom;
import com.cooba.component.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final User user;
    private final ChatRoom chatRoom;

    @Override
    public void joinRoom(long userId, long roomId) {
        user.verify(userId);
        chatRoom.verify(roomId);

        user.joinRoom(userId, roomId);
    }

    @Override
    public void leaveRoom(long userId) {
        user.verify(userId);

        user.leaveRoom(userId);
    }

    @Override
    public void speak(long userId, String message) {
        user.verify(userId);

        user.speak(userId, message);
    }
}
