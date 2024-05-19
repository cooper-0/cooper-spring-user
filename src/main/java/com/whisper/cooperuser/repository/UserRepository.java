package com.whisper.cooperuser.repository;

import com.whisper.cooperuser.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<UserEntity> findByName(String name); // 'username' 대신 'name'으로 수정
}
