package com.whisper.cooperuser.repository;

import com.whisper.cooperuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

//    Optional<User> findByPhoneNum(String phoneNum);
//
//    Optional<User> findByRefreshToken(String refreshToke);
}
