package com.cool.spring.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        String name,

        @Email
        String email,

        @Size(min = 8)
        String password
        ) {
}
