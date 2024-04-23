package com.cooba.controller;

import com.cooba.entity.ChatEntity;
import com.cooba.entity.UserEntity;
import com.cooba.request.AddMemberRequest;
import com.cooba.request.CreateRoomRequest;
import com.cooba.request.DeleteMemberRequest;
import com.cooba.request.DeleteRoomRequest;
import com.cooba.service.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chat-room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<Long> createRoom(@Valid @RequestBody CreateRoomRequest request) {
        Long roomId = chatRoomService.create(request.getUserId(), request.getName());
        return ResponseEntity.ok(roomId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteRoom(@Valid @RequestBody DeleteRoomRequest request) {
        boolean deleted = chatRoomService.delete(request.getUserId(), request.getRoomId());
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/history/{roomId}")
    public ResponseEntity<List<ChatEntity>> getHistory(@PathVariable String roomId) {
        List<ChatEntity> history = chatRoomService.getHistory(Long.parseLong(roomId));
        return ResponseEntity.ok(history);
    }

    @GetMapping("/recent/{roomId}")
    public ResponseEntity<List<ChatEntity>> getRecentChat(@PathVariable String roomId) {
        List<ChatEntity> recentChat = chatRoomService.getRecentChat(Long.parseLong(roomId));
        return ResponseEntity.ok(recentChat);
    }

    @PostMapping("/member/add")
    public ResponseEntity<?> addMember(@Valid @RequestBody AddMemberRequest request) {
        chatRoomService.addMember(request.getRoomId(), request.getUserId());
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/member/delete")
    public ResponseEntity<?> removeMember(@Valid @RequestBody DeleteMemberRequest request) {
       chatRoomService.removeMember(request.getRoomId(), request.getUserId());
        return ResponseEntity.ok(true);
    }

    @GetMapping("/member/{roomId}")
    public ResponseEntity<List<UserEntity>> getMembers(@PathVariable String roomId) {
        List<UserEntity> members = chatRoomService.getMembers(Long.parseLong(roomId));
        return ResponseEntity.ok(members);
    }
}
