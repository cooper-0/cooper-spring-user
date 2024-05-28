package com.whisper.cooperuser.service;

import com.whisper.cooperuser.dto.SignUpDto;
import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.entity.UserEntity;
import com.whisper.cooperuser.jwt.UserDetailsImpl;
import com.whisper.cooperuser.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.user.id")
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

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean idExists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    // 현재 로그인한 사용자 정보를 반환하는 메서드
    public UserEntity getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loadUserByEmail(userDetails.getUser().getEmail());
    }

    public UserEntity loadUserByEmail(String email) throws IllegalArgumentException {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 이메일"));
    }

    @Transactional
    //개별 유저를 조회하는 메서드
    public Optional<UserEntity> searchUser(String id, String email, String name) {
        if (id != null && !id.isEmpty()) {
            return userRepository.findById(Long.parseLong(id));
        } else if (email != null && !email.isEmpty()) {
            return userRepository.findByEmail(email);
        } else if (name != null && !name.isEmpty()) {
            return userRepository.findByName(name);
        }
        return Optional.empty();
    }
    // 모든 유저 정보를 반환하는 메서드
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getPassword(),
                        user.getRole()))
                .collect(Collectors.toList());
    }
    // RestTemplate 사용 전체 유저 리스트
    public List<UserDto> getAllUsersFromApi() {
        String url = "http:///cooper-user/users"; // 실제 사용 API URL로 변경해 주세요!
        UserDto[] users = restTemplate.getForObject(url, UserDto[].class);
        return Arrays.asList(users);
    }

    // RestTemplate 사용 개별 유저 조회
    public UserDto getUserFromApi(String id, String email, String name) {
        String url = "http://cooper-user/users"; // 실제 API URL로 변경해 주세요!

        if (id != null && !id.isEmpty()) {
            url += "id=" + id;
        } else if (email != null && !email.isEmpty()) {
            url += "email=" + email;
        } else if (name != null && !name.isEmpty()) {
            url += "name=" + name;
        }

        return restTemplate.getForObject(url, UserDto.class);
    }
}
