package com.whisper.cooperuser.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.jwt.UserDetailsImpl;
import com.whisper.cooperuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
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

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUserByEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("이메일이 필요합니다.");
        }
        try {
            if (!userService.emailExists(email)) {
                return ResponseEntity.status(404).body("해당 이메일을 가진 사용자가 없습니다.");
            }
            userService.deleteByEmail(email);
            return ResponseEntity.ok("유저 삭제가 완료되었습니다");
        } catch (Exception e) {
            log.error("삭제에 실패했습니다: {}", email, e);
            return ResponseEntity.status(500).body("삭제를 실패했습니다");
        }
    }

    @DeleteMapping("/user/id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            if (!userService.idExists(id)) {
                return ResponseEntity.status(404).body("해당 ID를 가진 사용자가 없습니다.");
            }
            userService.deleteById(id);
            return ResponseEntity.ok("유저 삭제가 완료되었습니다");
        } catch (Exception e) {
            log.error("삭제에 실패했습니다: {}", id, e);
            return ResponseEntity.status(500).body("삭제를 실패했습니다");
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        UserDto userDto = userService.loadUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, String> request) {
        Long id;
        String name = request.get("name");
        String password = request.get("password");

        try {
            id = Long.parseLong(request.get("id"));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("유효한 ID가 필요합니다.");
        }

        if (name == null || password == null) {
            return ResponseEntity.badRequest().body("이름과 비밀번호가 필요합니다.");
        }

        try {
            if (!userService.idExists(id)) {
                return ResponseEntity.status(404).body("해당 ID를 가진 사용자가 없습니다.");
            }
            userService.updateUser(id, name, password);
            return ResponseEntity.ok("유저 정보가 업데이트되었습니다");
        } catch (Exception e) {
            log.error("업데이트에 실패했습니다: {}", id, e);
            return ResponseEntity.status(500).body("업데이트를 실패했습니다");
        }
    }

    // 새로운 엔드포인트 추가
    @GetMapping("/alluser")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
