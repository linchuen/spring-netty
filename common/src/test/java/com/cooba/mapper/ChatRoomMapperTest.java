package com.cooba.mapper;

import com.cooba.entity.ChatRoomEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ChatRoomMapper.class)
class ChatRoomMapperTest extends MapperTest{
    @Autowired
    ChatRoomMapper chatRoomMapper;
    @Test
    void insert() {
        chatRoomMapper.insert(Instancio.create(ChatRoomEntity.class));
    }
}