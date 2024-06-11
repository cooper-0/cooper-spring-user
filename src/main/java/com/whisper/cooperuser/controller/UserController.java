//UserController
package com.whisper.cooperuser.controller;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.jwt.UserDetailsImpl;
import com.whisper.cooperuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import com.whisper.cooperuser.entity.UserEntity;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cooper-user")
public class UserController {
    @Autowired
    private UserService userService;
    //현재 로그인 되어 있는 유저 정보
    @GetMapping("/user/me/{id}")
    @PreAuthorize("#id == authentication.principal.user.id")
    public ResponseEntity<UserDto> getMyUserInfo(@PathVariable Long id) {
        // 현재 인증된 사용자의 정보를 가져옴
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!id.equals(userDetails.getUser().getId())) {
            return ResponseEntity.status(403).body(null); // 403 필요 권한 없음 (본인이 아님)
        }
        UserDto userDto = userDetails.getUser();
        return ResponseEntity.ok(userDto);
    }

    //유저 삭제
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.user.id")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            if (!userService.idExists(id)) {
                return ResponseEntity.status(406).body("해당 ID를 가진 사용자가 없습니다.");
            }
            userService.deleteById(id);
            return ResponseEntity.ok("유저 삭제가 완료되었습니다");
        } catch (Exception e) {
            log.error("삭제에 실패했습니다: {}", id, e);
            return ResponseEntity.status(408).body("삭제를 실패했습니다");
        }
    }



    //모든 유저 조회
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    //개별 유저 조회
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> searchUser(@PathVariable String id) {
        Optional<UserEntity> userEntity = userService.searchUser(id, null, null);
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
/*
    // RestTemplate 사용 전체 리스트 조회
    @PostMapping("/users")
    public ResponseEntity<String> sendUserList() {
        userService.sendUserListToApi();
        return ResponseEntity.ok("성공");
    }

    // RestTemplate 사용 개별 유저 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserFromApi(@PathVariable String id) {
        UserDto user = userService.getUserFromApi(id);
        return ResponseEntity.ok(user);
    }
    
 */
}
