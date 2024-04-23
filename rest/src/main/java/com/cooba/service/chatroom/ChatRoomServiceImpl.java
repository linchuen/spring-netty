package com.cooba.service.chatroom;

import com.cooba.component.chatroom.ChatRoom;
import com.cooba.component.messsage_publisher.MessagePublisher;
import com.cooba.component.user.User;
import com.cooba.dto.MqMessage;
import com.cooba.entity.ChatEntity;
import com.cooba.entity.ChatRoomEntity;
import com.cooba.entity.UserEntity;
import com.cooba.enums.MessageType;
import com.cooba.exception.WrongOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final User user;
    private final ChatRoom chatRoom;
    private final MessagePublisher messagePublisher;

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
        UserEntity userEntity = user.verify(userId);
        String name = userEntity.getName();
        ChatRoomEntity chatRoomEntity = chatRoom.verify(roomId);
        String roomName = chatRoomEntity.getName();

        List<UserEntity> members = chatRoom.getMembers(roomId);
        boolean isMember = members.stream().anyMatch(member -> member.getId().equals(userId));
        if (isMember) throw new WrongOperationException("is already member");

        String systemMessage = name + "加入" + roomName + "房間";
        chatRoom.addMember(roomId, userId);
        chatRoom.addSystemMessage(roomId, userId, MessageType.JOIN, systemMessage);

        for (UserEntity member : members) {
            Long memberId = member.getId();

            if (userId == memberId) continue;
            MqMessage mqMessage = MqMessage.builder()
                    .userId(String.valueOf(memberId))
                    .roomId(roomId)
                    .type(MessageType.JOIN)
                    .message(systemMessage)
                    .build();
            messagePublisher.sendMessage(mqMessage);
        }
    }

    @Override
    public void removeMember(long roomId, long userId) {
        UserEntity userEntity = user.verify(userId);
        String name = userEntity.getName();
        ChatRoomEntity chatRoomEntity = chatRoom.verify(roomId);
        String roomName = chatRoomEntity.getName();

        List<UserEntity> members = chatRoom.getMembers(roomId);
        boolean isMember = members.stream().anyMatch(member -> member.getId().equals(userId));
        if (!isMember) throw new WrongOperationException("is not member");

        String systemMessage = name + "離開" + roomName + "房間";
        chatRoom.removeMember(roomId, userId);
        chatRoom.addSystemMessage(roomId, userId, MessageType.LEAVE, systemMessage);

        for (UserEntity member : members) {
            Long memberId = member.getId();

            if (userId == memberId) continue;
            MqMessage mqMessage = MqMessage.builder()
                    .userId(String.valueOf(memberId))
                    .roomId(roomId)
                    .type(MessageType.LEAVE)
                    .message(systemMessage)
                    .build();
            messagePublisher.sendMessage(mqMessage);
        }
    }

    @Override
    public List<UserEntity> getMembers(long roomId) {
        chatRoom.verify(roomId);

        return chatRoom.getMembers(roomId);
    }
}
