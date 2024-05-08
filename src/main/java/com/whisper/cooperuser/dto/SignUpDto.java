package com.whisper.cooperuser.dto;

import com.whisper.cooperuser.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=2, message = "닉네임이 너무 짧습니다.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotBlank
    private String checkedPassword;

    @Builder.Default
    private Role role = Role.ROLE_USER; // 기본값 설정

    @Builder
    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(email)
                .name(name)
                .password(password)
                .role(role)
                .build();
    }
}
