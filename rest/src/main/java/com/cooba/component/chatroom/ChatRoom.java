package com.cooba.component.chatroom;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.UserEntity;

import java.util.List;

public interface ChatRoom {

    void create(String name);

    void delete(long roomId);

    List<ChatEntity> getHistory(long roomId);

    List<ChatEntity> getRecentChat(long roomId);

    void addMember(long roomId, long userId);

    void removeMember(long roomId, long userId);

    List<UserEntity> getMembers(long roomId);
}
