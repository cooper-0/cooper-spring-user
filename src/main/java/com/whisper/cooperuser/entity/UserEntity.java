package com.whisper.cooperuser.entity;

import com.whisper.cooperuser.dto.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // db가 id를 자동 생성하게
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true, length = 45) // length는 나중에 전체적으로 수정
    private String email;

    @Setter
    @Column(nullable = false, length = 45)
    private String name;

    @Setter
    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

}
