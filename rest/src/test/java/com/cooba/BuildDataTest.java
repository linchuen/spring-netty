package com.cooba;

import com.cooba.component.user.User;
import com.cooba.service.chatroom.ChatRoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BuildDataTest {

    @Autowired
    User user;
    @Autowired
    ChatRoomService chatRoomService;

    @Test
    @DisplayName("建立測試用戶")
    public void buildUser() {
        List<String> names = List.of("Aiden", "Brace", "Caesar", "Daniel", "Edward", "Floyd",
                "Gordon", "Hank", "Ian", "Jason");

        for (String name : names) {
            Long id = user.create(name);
            Assertions.assertNotNull(id);
        }
    }

    @Test
    @DisplayName("建立測試聊天室")
    public void buildChatRoom() {
        List<String> names = List.of("Chat1", "Chat2", "Chat3", "Chat4", "Chat5");

        for (String name : names) {
            Long id = chatRoomService.create(1, name);
            Assertions.assertNotNull(id);
        }
    }
}