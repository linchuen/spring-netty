package com.cooba.mapper;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.ChatRoomEntity;
import com.cooba.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMapper {
    void insert(ChatRoomEntity chatRoom);

    void delete(long roomId);

    Optional<ChatRoomEntity> selectById(long roomId);

    void insertMember(long roomId, long userId);

    void deleteMember(long roomId, long userId);

    List<UserEntity> selectMember(long roomId);

    void insertChat(ChatEntity chat);

    void deleteChat(long chatId);

    List<ChatEntity> selectChat(long roomId , Integer limit);
}
