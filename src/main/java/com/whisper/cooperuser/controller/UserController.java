package com.whisper.cooperuser.controller;

import com.whisper.cooperuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cooper-user")
public class UserController {
    @Autowired
    private UserService userService;

    // 유저 조회, 삭제, 수정 등 처리할 컨트롤러

    @GetMapping("/user")
    public ResponseEntity<String> getMyUserInfo() {
        return ResponseEntity.ok("user");
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<String> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok("admin");
    }
}
