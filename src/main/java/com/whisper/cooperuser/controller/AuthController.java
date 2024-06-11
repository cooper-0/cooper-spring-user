package com.whisper.cooperuser.controller;

import com.whisper.cooperuser.dto.SignInDto;
import com.whisper.cooperuser.dto.SignUpDto;
import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.service.AuthService;
import com.whisper.cooperuser.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cooper-user")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpDto user) throws Exception {
        log.info(user.toString());
        Long id = userService.signUp(user);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto user) {
        log.info(user.toString());
        try {
            String token = authService.signIn(user);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }
}
