package com.spring.api.web.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDto {

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}