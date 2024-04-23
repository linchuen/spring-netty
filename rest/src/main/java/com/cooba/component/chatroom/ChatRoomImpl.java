package com.cooba.component.chatroom;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.ChatRoomEntity;
import com.cooba.entity.UserEntity;
import com.cooba.enums.MessageType;
import com.cooba.exception.ValueNotExistException;
import com.cooba.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomImpl implements ChatRoom {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoomEntity create(String name) {
        ChatRoomEntity chatRoom = new ChatRoomEntity(name);
        chatRoomRepository.insert(chatRoom);

        return chatRoom;
    }

    @Override
    public boolean delete(long roomId) {
        chatRoomRepository.deleteMember(roomId, null);
        return chatRoomRepository.delete(roomId) == 1;
    }

    @Override
    public ChatRoomEntity verify(long roomId) {
        return chatRoomRepository.selectById(roomId)
                .orElseThrow(() -> new ValueNotExistException("chatRoom"));
    }

    @Override
    public List<ChatEntity> getHistory(long roomId) {
        return chatRoomRepository.selectChat(roomId, null);
    }

    @Override
    public List<ChatEntity> getRecentChat(long roomId) {
        return chatRoomRepository.selectChat(roomId, 50);
    }

    @Override
    public void addMember(long roomId, long userId) {
        chatRoomRepository.insertMember(roomId, userId);
    }

    @Override
    public void removeMember(long roomId, long userId) {
        chatRoomRepository.deleteMember(roomId, userId);
    }

    @Override
    public void addSystemMessage(long roomId, long userId, MessageType type, String message) {
        ChatEntity chat = new ChatEntity();
        chat.setUserId(userId);
        chat.setRoomId(roomId);
        chat.setType(type.name());
        chat.setMessage(message);
        chatRoomRepository.insertChat(chat);
    }

    @Override
    public List<UserEntity> getMembers(long roomId) {
        return chatRoomRepository.selectMember(roomId);
    }
}
