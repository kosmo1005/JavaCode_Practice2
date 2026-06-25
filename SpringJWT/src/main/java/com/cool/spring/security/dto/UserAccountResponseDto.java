package com.cool.spring.security.dto;
import com.cool.spring.security.dao.entity.Role;
import java.util.Set;

public record UserAccountResponseDto(
        Long id,
        String username,
        String email,
        Set<Role> roles,
        boolean enabled
) {
}
