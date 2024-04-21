package com.whisper.cooperuser.service;

import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.entity.User;
//import com.whisper.cooperuser.entity.UserDetailsImpl;
import com.whisper.cooperuser.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException(email));
//
//        return new UserDetailsImpl(user);
//    }

    public User loadUserByEmail(String email) throws IllegalArgumentException {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 이메일"));
    }

    @Transactional
    public Long create(UserDto dto) {
        try (User user = loadUserByEmail(dto.getEmail())) {
            log.info("사용중인 이메일");
            return null;
        } catch (Exception e) {
            log.error(e.toString());
            return userRepository.save(dto.toEntity()).getId();
        }
    }

    public User show(String email) throws Exception {
        try (User user = loadUserByEmail(email)) {
            return user;
        } catch (Exception e) {
            log.error(e.toString());
            throw e;
        }
    }

    public List<User> getUserlList() {
        return userRepository.findAll();
    }
}
