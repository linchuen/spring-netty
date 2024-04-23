package com.cooba.component.chatroom;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.ChatRoomEntity;
import com.cooba.entity.UserEntity;
import com.cooba.enums.MessageType;

import java.util.List;

public interface ChatRoom {

    ChatRoomEntity create(String name);

    boolean delete(long roomId);

    ChatRoomEntity verify(long roomId);

    List<ChatEntity> getHistory(long roomId);

    List<ChatEntity> getRecentChat(long roomId);

    void addMember(long roomId, long userId);

    void removeMember(long roomId, long userId);

    void addSystemMessage(long roomId, long userId, MessageType type, String message);

    List<UserEntity> getMembers(long roomId);
}
