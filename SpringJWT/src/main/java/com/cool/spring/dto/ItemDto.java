package com.cool.spring.dto;

import com.cool.spring.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDto {

    @JsonView(Views.OrderDetail.class)
    private Long id;

    @JsonView(Views.OrderDetail.class)
    @NotBlank(message = "Item name must not be blank")
    private String name;

    @JsonView(Views.OrderDetail.class)
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
}
