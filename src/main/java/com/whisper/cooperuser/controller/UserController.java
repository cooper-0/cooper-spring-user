package com.whisper.cooperuser.controller;

import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.jwt.UserDetailsImpl;
import com.whisper.cooperuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cooper-user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDto> getMyUserInfo() {
        // 현재 인증된 사용자의 정보를 가져옴
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = userDetails.getUser();
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        try {
            userService.deleteByEmail(email);
            return ResponseEntity.ok("유저 삭제가 완료되었습니다");
        } catch (Exception e) {
            log.error("삭제에 실패했습니다: {}", email, e);
            return ResponseEntity.badRequest().body("삭제를 실패했습니다");
        }
    }

    @DeleteMapping("/user/id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("유저 삭제가 완료되었습니다");
        } catch (Exception e) {
            log.error("삭제에 실패했습니다: {}", id, e);
            return ResponseEntity.badRequest().body("삭제를 실패했습니다");
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        UserDto userDto = userService.loadUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String password) {
        try {
            userService.updateUser(id, name, password);
            return ResponseEntity.ok("유저 정보가 업데이트되었습니다");
        } catch (Exception e) {
            log.error("업데이트에 실패했습니다: {}", id, e);
            return ResponseEntity.badRequest().body("업데이트를 실패했습니다");
        }
    }
}
