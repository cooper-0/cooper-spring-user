package com.whisper.cooperuser.dto;

import com.whisper.cooperuser.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {
    private Long id;

    private String email;

    private String name;

    @JsonIgnore
    private String password;

    private Role role;

    @Builder
    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .email(email)
                .name(name)
                .role(role)
                .password(password)
                .build();
    }
}