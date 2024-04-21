package com.whisper.cooperuser.controller;

import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.entity.User;
import com.whisper.cooperuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<Long> addUser(@RequestBody UserDto dto) {
        log.info(dto.toString());
        Long id = userService.create(dto);

        return id != null ?
                ResponseEntity.status(HttpStatus.OK).body(id) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) throws Exception {
        User user = userService.show(email);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUserList() {
        List<User> userList = userService.getUserlList();

        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
}
