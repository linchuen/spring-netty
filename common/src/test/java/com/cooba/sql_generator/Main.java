package com.cooba.sql_generator;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.ChatRoomEntity;
import com.cooba.entity.ChatRoomMemberEntity;
import com.cooba.entity.UserEntity;

public class Main {
    public static void main(String[] args) {
        TableSqlGenerator.generateSql(ChatRoomEntity.class);
    }
}
