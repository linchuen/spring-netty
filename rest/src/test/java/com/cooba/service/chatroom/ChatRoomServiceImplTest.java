package com.cooba.service.chatroom;

import com.cooba.component.chatroom.ChatRoom;
import com.cooba.component.messsage_publisher.MessagePublisher;
import com.cooba.component.user.User;
import com.cooba.entity.UserEntity;
import com.cooba.exception.WrongOperationException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceImplTest {
    @InjectMocks
    ChatRoomServiceImpl chatRoomService;
    @Mock
    User user;
    @Mock
    ChatRoom chatRoom;
    @Mock
    MessagePublisher messagePublisher;

    @Test
    @DisplayName("新增已存在成員")
    void addExistMember() {
        UserEntity testUser = Instancio.create(UserEntity.class);
        testUser.setId(1L);
        Mockito.when(user.verify(1L)).thenReturn(testUser);
        Mockito.when(chatRoom.getMembers(1L)).thenReturn(List.of(testUser));

        Assertions.assertThrows(WrongOperationException.class,
                () -> chatRoomService.addMember(1L, 1L));
    }

    @Test
    @DisplayName("新增成員")
    void addMember() {
        UserEntity testUser = Instancio.create(UserEntity.class);
        testUser.setId(1L);
        Mockito.when(user.verify(1L)).thenReturn(testUser);
        Mockito.when(chatRoom.getMembers(1L)).thenReturn(List.of());

        chatRoomService.addMember(1L, 1L);

        Mockito.verify(chatRoom).addMember(1L,1L);
    }

    @Test
    @DisplayName("移除不在成員")
    void removeNotExistMember() {
        UserEntity testUser = Instancio.create(UserEntity.class);
        testUser.setId(1L);
        Mockito.when(user.verify(1L)).thenReturn(testUser);
        Mockito.when(chatRoom.getMembers(1L)).thenReturn(List.of());

        Assertions.assertThrows(WrongOperationException.class,
                () -> chatRoomService.removeMember(1L, 1L));
    }

    @Test
    @DisplayName("移除存在成員")
    void removeExistMember() {
        UserEntity testUser = Instancio.create(UserEntity.class);
        testUser.setId(1L);
        Mockito.when(user.verify(1L)).thenReturn(testUser);
        Mockito.when(chatRoom.getMembers(1L)).thenReturn(List.of(testUser));

        chatRoomService.removeMember(1L, 1L);

        Mockito.verify(chatRoom).removeMember(1L,1L);
    }
}