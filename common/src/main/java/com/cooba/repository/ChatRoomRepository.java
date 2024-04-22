package com.cooba.repository;

import com.cooba.mapper.ChatRoomMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChatRoomRepository extends ChatRoomMapper {
}
