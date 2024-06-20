package com.whisper.cooperuser.service;

import com.whisper.cooperuser.dto.SignInDto;
import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.entity.UserEntity;
import com.whisper.cooperuser.jwt.JwtUtil;
import com.whisper.cooperuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public Map<String, Object> signIn(SignInDto user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail()).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        if (!encoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        UserDto userDto = new UserDto(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getPassword(),
                userEntity.getRole()
        );

        String token = jwtUtil.createToken(userDto);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", userDto.getId());
        response.put("name", userDto.getName());
        response.put("role", userDto.getRole());

        return response;
    }
}
