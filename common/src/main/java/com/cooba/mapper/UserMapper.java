package com.cooba.mapper;

import com.cooba.entity.UserData;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserData> findById(long userId);
}
