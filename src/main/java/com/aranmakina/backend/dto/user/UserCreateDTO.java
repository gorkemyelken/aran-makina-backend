package com.aranmakina.backend.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDTO {
    private Long userId;
    private String userName;
    private String password;
    private String role;
}
