package com.cooba.mapper;

import com.cooba.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserEntity> findById(long userId);
}
