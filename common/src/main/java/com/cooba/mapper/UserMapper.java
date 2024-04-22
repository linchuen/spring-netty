package com.cooba.mapper;

import com.cooba.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    void insert(UserEntity user);

    void delete(long userId);

    Optional<UserEntity> findById(long userId);
}
