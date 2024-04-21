package com.whisper.cooperuser.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity implements AutoCloseable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // db가 id를 자동 생성하게
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false) // length = 20, 이름 길이 제한
    private String name;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false) // length = 20
    private String password;

    @Column(length = 1000)
    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    @Override
    public void close() throws Exception {

    }

//    @Enumerated(EnumType.STRING)
//    @JsonIgnore
//    private PublicStatus publicStatus;
//
//    @JsonIgnore
//    @Enumerated(EnumType.STRING)
//    private ShareStatus shareStatus;

//    private String imgUrl;
}
