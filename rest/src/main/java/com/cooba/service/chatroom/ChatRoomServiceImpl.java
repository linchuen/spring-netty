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
        chatRoom.verify(roomId);

        List<UserEntity> members = chatRoom.getMembers(roomId);
        boolean isMember = members.stream().anyMatch(member -> member.getId().equals(userId));
        if (isMember) throw new WrongOperationException("is already member");

        chatRoom.addMember(roomId, userId);

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

        List<UserEntity> members = chatRoom.getMembers(roomId);
        boolean isMember = members.stream().anyMatch(member -> member.getId().equals(userId));
        if (!isMember) throw new WrongOperationException("is not member");

        chatRoom.removeMember(roomId, userId);

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
