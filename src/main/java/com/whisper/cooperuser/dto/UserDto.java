package com.whisper.cooperuser.dto;

import com.whisper.cooperuser.entity.User;
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

    private String name;

    private String email;

    private String password;

    public User toEntity() {
        return new User(id, name, email, password, null);
    }
}
