package com.cooba.service.user;

import com.cooba.component.chatroom.ChatRoom;
import com.cooba.component.messsage_publisher.MessagePublisher;
import com.cooba.component.user.User;
import com.cooba.entity.UserEntity;
import com.cooba.enums.MessageType;
import com.cooba.exception.NotInRoomException;
import com.cooba.exception.PermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final User user;
    private final ChatRoom chatRoom;
    private final MessagePublisher messagePublisher;

    @Override
    public void enterRoom(long userId, long roomId) {
        user.verify(userId);
        chatRoom.verify(roomId);

        boolean isMember = chatRoom.getMembers(roomId).stream()
                .anyMatch(userEntity -> userEntity.getId().equals(userId));
        if (!isMember) throw new PermissionException();

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

        Long currentRoomId = user.getCurrentRoomId(userId);
        if (currentRoomId == null) throw new NotInRoomException(String.valueOf(userId));

        user.speak(userId, currentRoomId, message);

        List<UserEntity> members = chatRoom.getMembers(currentRoomId);
        for (UserEntity member : members) {
            Long memberId = member.getId();

            messagePublisher.sendMessage(String.valueOf(memberId), MessageType.MESSAGE, message);
        }
    }
}
