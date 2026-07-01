package com.cool.spring.dto;

import lombok.Builder;

@Builder
public record UserAccountResponseDto(Long id, String email, String name) {

}
