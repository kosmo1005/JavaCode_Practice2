package com.cool.spring.dto;

import com.cool.spring.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderDto {

    @JsonView(Views.Summary.class)
    private Long id;

    @JsonView(Views.OrderDetail.class)
    @Valid
    private List<ItemDto> items;

    @JsonView(Views.Summary.class)
    @Positive(message = "Amount must be positive")
    private Double amount;

    @JsonView(Views.OrderDetail.class)
    private String status;

    @JsonView(Views.OrderDetail.class)
    private UserDto user;
}
