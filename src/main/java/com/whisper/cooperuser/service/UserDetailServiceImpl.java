package com.whisper.cooperuser.service;

import com.whisper.cooperuser.dto.UserDto;
import com.whisper.cooperuser.entity.UserEntity;
import com.whisper.cooperuser.jwt.UserDetailsImpl;
import com.whisper.cooperuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

        UserDto userDto = new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getRole()
        );

        return new UserDetailsImpl(userDto);
    }
}
