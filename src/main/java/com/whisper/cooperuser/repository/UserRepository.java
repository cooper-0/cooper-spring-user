package com.whisper.cooperuser.repository;

import com.whisper.cooperuser.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

//    Optional<UserEntity> findByRefreshToken(String refreshToke);
}
