package com.spring.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDto {

    @NotBlank(message = "Current password cannot be empty")
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String currentPassword;
    @NotBlank(message = "New password cannot be empty")
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String newPassword;
    @NotBlank(message = "New password cannot be empty")
    @Size(min = 6, max = 6, message = "Password size must be exactly 6 characters")
    private String confirmNewPassword;
}