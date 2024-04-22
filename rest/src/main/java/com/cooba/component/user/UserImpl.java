package com.cooba.component.user;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.UserEntity;
import com.cooba.enums.RedisKey;
import com.cooba.exception.ValueNotExistException;
import com.cooba.repository.ChatRoomRepository;
import com.cooba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserImpl implements User {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long create(String name) {
        UserEntity user = new UserEntity();
        user.setName(name);
        userRepository.insert(user);
        return user.getId();
    }

    @Override
    public boolean delete(long userId) {
        return userRepository.delete(userId) == 1;
    }

    @Override
    public UserEntity verify(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ValueNotExistException("user"));
    }

    @Override
    public void joinRoom(long userId, long roomId) {
        redisTemplate.opsForHash().put(RedisKey.USER_ROOM.name(), userId, roomId);
    }

    @Override
    public void leaveRoom(long userId) {
        redisTemplate.opsForHash().delete(RedisKey.USER_ROOM.name(), userId);
    }

    @Override
    public void speak(long userId, long roomId, String message) {
        ChatEntity chat = new ChatEntity();
        chat.setUserId(userId);
        chat.setRoomId(roomId);
        chat.setMessage(message);
        chatRoomRepository.insertChat(chat);
    }

    @Override
    public Long getCurrentRoomId(long userId) {
        return (Long) redisTemplate.opsForHash().get(RedisKey.USER_ROOM.name(), userId);
    }
}
