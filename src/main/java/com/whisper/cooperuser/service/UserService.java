package com.whisper.cooperuser.service;

import com.whisper.cooperuser.dto.SignUpDto;
import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.entity.UserEntity;
import com.whisper.cooperuser.jwt.UserDetailsImpl;
import com.whisper.cooperuser.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public Long signUp(SignUpDto requestDto) throws Exception {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new Exception("사용 중인 이메일입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getCheckedPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        UserEntity user = userRepository.save(requestDto.toEntity());
        user.encodePassword(passwordEncoder);

        return user.getId();
    }

    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, String name, String password) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 사용자가 없습니다."));
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public List<UserEntity> getUserList() {
        return userRepository.findAll();
    }

    // 현재 로그인한 사용자 정보를 반환하는 메서드
    public UserEntity getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loadUserByEmail(userDetails.getUser().getEmail());
    }

    public UserEntity loadUserByEmail(String email) throws IllegalArgumentException {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 이메일"));
    }

    // username으로 UserDto를 반환하는 메서드
    public UserDto loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("없는 사용자 이름"));
        return new UserDto(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }
}
