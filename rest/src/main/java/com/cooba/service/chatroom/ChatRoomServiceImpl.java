package com.cooba.service.chatroom;

import com.cooba.component.chatroom.ChatRoom;
import com.cooba.component.messsage_publisher.MessagePublisher;
import com.cooba.component.user.User;
import com.cooba.dto.MqMessage;
import com.cooba.entity.ChatEntity;
import com.cooba.entity.ChatRoomEntity;
import com.cooba.entity.UserEntity;
import com.cooba.enums.MessageType;
import com.cooba.exception.NotInRoomException;
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
        chatRoom.verify(roomId);

        chatRoom.addMember(roomId, userId);

        List<UserEntity> members = chatRoom.getMembers(roomId);
        for (UserEntity member : members) {
            Long memberId = member.getId();

            if (userId == memberId) continue;
            MqMessage mqMessage = MqMessage.builder()
                    .userId(String.valueOf(userId))
                    .roomId(roomId)
                    .type(MessageType.JOIN)
                    .message(name + " joins room")
                    .build();
            messagePublisher.sendMessage(mqMessage);
        }
    }

    @Override
    public void removeMember(long roomId, long userId) {
        UserEntity userEntity = user.verify(userId);
        String name = userEntity.getName();

        Long currentRoomId = user.getCurrentRoomId(userId);
        if (currentRoomId == null) throw new NotInRoomException(String.valueOf(userId));

        chatRoom.removeMember(currentRoomId, userId);

        List<UserEntity> members = chatRoom.getMembers(currentRoomId);
        for (UserEntity member : members) {
            Long memberId = member.getId();

            if (userId == memberId) continue;
            MqMessage mqMessage = MqMessage.builder()
                    .userId(String.valueOf(userId))
                    .roomId(roomId)
                    .type(MessageType.LEAVE)
                    .message(name + " leaves room")
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
