package com.whisper.cooperuser.service;

import com.whisper.cooperuser.dto.SignUpDto;
import com.whisper.cooperuser.entity.UserEntity;
import com.whisper.cooperuser.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity loadUserByEmail(String email) throws IllegalArgumentException {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 이메일"));
    }

    @Transactional
    public Long signUp(SignUpDto requestDto) throws Exception {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new Exception("사용 중인 이메일입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        UserEntity user = userRepository.save(requestDto.toEntity());
        user.encodePassword(passwordEncoder);

        return user.getId();
    }

    public List<UserEntity> getUserlList() {
        return userRepository.findAll();
    }
}
