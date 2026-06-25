package com.cool.spring.dto;

import com.cool.spring.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDto {

    @JsonView(Views.Summary.class)
    private Long id;

    @JsonView(Views.Summary.class)
    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name too long")
    private String name;

    @JsonView(Views.UserDetail.class)
    @Email(message = "Invalid username format")
    @Size(max = 255, message = "Email too long")
    private String email;

    @JsonView(Views.UserDetail.class)
    private List<OrderDto> orders;
}
