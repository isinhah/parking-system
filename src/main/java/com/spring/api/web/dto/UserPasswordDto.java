package com.spring.api.web.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDto {

    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String currentPassword;
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String newPassword;
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String confirmNewPassword;
}