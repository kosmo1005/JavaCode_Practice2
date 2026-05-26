package com.cool.spring.controller;

import com.cool.spring.dto.ItemDto;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.dto.PageResponse;
import com.cool.spring.service.OrderService;
import com.cool.spring.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService svc;

    /**
     * Получить все заказы пользователя
     */
    @GetMapping
    @JsonView(Views.Summary.class)
    public ResponseEntity<PageResponse<OrderDto>> getAllOrders(
            @RequestParam @NotNull @Positive Long userId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(svc.getAllOrders(pageable, userId));
    }

    /**
     * Получить заказ по id
     */
    @GetMapping("/{id}")
    @JsonView(Views.OrderDetail.class)
    public ResponseEntity<OrderDto> getOrderById(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(svc.getOrderById(id));
    }

    /**
     * Создать заказ для пользователя
     */
    @PostMapping
    @JsonView(Views.OrderDetail.class)
    public ResponseEntity<OrderDto> createOrder(
            @RequestParam @NotNull @Positive Long userId,
            @RequestBody @Valid OrderDto data
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(svc.createOrder(data, userId));
    }

    /**
     * Обновить заказ
     */
    @PatchMapping("/{id}")
    @JsonView(Views.OrderDetail.class)
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid OrderDto data
    ) {
        return ResponseEntity.ok(
                svc.updateOrder(data, id)
        );
    }

    /**
     * Удалить заказ
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable @NotNull @Positive Long id) {

        svc.deleteOrder(id);

        return ResponseEntity.ok(
                "Order deleted successfully"
        );
    }

    /**
     * Добавить item в заказ
     */
    @PostMapping("/{id}/items")
    @JsonView(Views.OrderDetail.class)
    public ResponseEntity<OrderDto> addItemForOrder(
            @PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid ItemDto data
    ) {

        return ResponseEntity.ok(
                svc.addItemForOrder(data, id)
        );
    }
}