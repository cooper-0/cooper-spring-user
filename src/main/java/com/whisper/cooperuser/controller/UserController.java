package com.whisper.cooperuser.controller;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.jwt.UserDetailsImpl;
import com.whisper.cooperuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.whisper.cooperuser.entity.UserEntity;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/cooper-user")
public class UserController {
    @Autowired
    private UserService userService;
    //현재 로그인 되어 있는 유저 정보
    @GetMapping("/user")
    public ResponseEntity<UserDto> getMyUserInfo() {
        // 현재 인증된 사용자의 정보를 가져옴
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = userDetails.getUser();
        return ResponseEntity.ok(userDto);
    }

   //유저 삭제 
    @DeleteMapping("/deleteuser")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            if (!userService.idExists(id)) {
                return ResponseEntity.status(406).body("해당 ID를 가진 사용자가 없습니다.");
            }
            userService.deleteById(id);
            return ResponseEntity.ok("유저 삭제가 완료되었습니다");
        } catch (Exception e) {
            log.error("삭제에 실패했습니다: {}", id, e);
            return ResponseEntity.status(500).body("삭제를 실패했습니다");
        }
    }



    //모든 유저 조회
    @GetMapping("/alluser")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    //개별 유저 조회
    @GetMapping("/searchuser")
    public ResponseEntity<UserDto> searchUser(@RequestBody Map<String, String> request) {
        String id = request.get("id");
        String email = request.get("email");
        String name = request.get("name");

        Optional<UserEntity> userEntity = userService.searchUser(id, email, name);
        if (userEntity.isPresent()) {
            UserDto userDto = new UserDto(
                    userEntity.get().getId(),
                    userEntity.get().getEmail(),
                    userEntity.get().getName(),
                    userEntity.get().getPassword(),
                    userEntity.get().getRole()
            );
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
