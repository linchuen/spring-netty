package com.cooba.service.chatroom;

import com.cooba.component.chatroom.ChatRoom;
import com.cooba.component.user.User;
import com.cooba.entity.ChatEntity;
import com.cooba.entity.ChatRoomEntity;
import com.cooba.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final User user;
    private final ChatRoom chatRoom;

    @Override
    public Long create(long userId, String name) {
        user.verify(userId);

        ChatRoomEntity chatRoomEntity = chatRoom.create(name);
        Long roomId = chatRoomEntity.getId();
        chatRoom.addMember(roomId, userId);
        return roomId;
    }

    @Override
    public boolean delete(long userId, long roomId) {
        user.verify(userId);
        chatRoom.verify(roomId);

        return chatRoom.delete(roomId);
    }

    @Override
    public List<ChatEntity> getHistory(long roomId) {
        chatRoom.verify(roomId);

        return chatRoom.getHistory(roomId);
    }

    @Override
    public List<ChatEntity> getRecentChat(long roomId) {
        chatRoom.verify(roomId);

        return chatRoom.getRecentChat(roomId);
    }

    @Override
    public void addMember(long roomId, long userId) {
        user.verify(userId);
        chatRoom.verify(roomId);

        chatRoom.addMember(roomId, userId);
    }

    @Override
    public void removeMember(long roomId, long userId) {
        user.verify(userId);
        chatRoom.verify(roomId);

        chatRoom.removeMember(roomId, userId);
    }

    @Override
    public List<UserEntity> getMembers(long roomId) {
        chatRoom.verify(roomId);

        return chatRoom.getMembers(roomId);
    }
}
