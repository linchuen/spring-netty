package com.cooba.component.user;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.UserEntity;
import com.cooba.enums.RedisKey;
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
    public void create(String name) {
        UserEntity user = new UserEntity();
        user.setName(name);
        userRepository.insert(user);
    }

    @Override
    public void delete(long userId) {
        userRepository.delete(userId);
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
    public void speak(long userId, String message) {
        ChatEntity chat = new ChatEntity();
        chat.setUserId(userId);
        chat.setMessage(message);
        chatRoomRepository.insertChat(chat);
    }

    @Override
    public Long getCurrentRoom(long userId) {
        return (Long) redisTemplate.opsForHash().get(RedisKey.USER_ROOM.name(), userId);
    }
}
