package com.cool.spring.controller;

import com.cool.spring.dto.ItemDto;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.dto.PageResponse;
import com.cool.spring.service.OrderService;
import com.cool.spring.view.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@Validated
public class OrderController {

    private final OrderService svc;
    private final ObjectMapper mapper;

    public OrderController(OrderService svc, ObjectMapper mapper) {
        this.svc = svc;
        this.mapper = mapper;

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Получить все заказы пользователя
     */
    @GetMapping
    @PreAuthorize("#userId == authentication.principal.id || " +
            "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    public ResponseEntity<String> getAllOrders(
            @RequestParam @NotNull @Positive Long userId,
            @PageableDefault(size = 20) Pageable pageable
    ) throws JsonProcessingException {

        PageResponse<OrderDto> pageResponse = svc.getAllOrders(pageable, userId);

        String jsonResult = toJson(pageResponse, Views.Summary.class);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Получить заказ по id
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN') || @orderAccessService.canAccess(#id, authentication.principal)")
    public ResponseEntity<String> getOrderById(
            @PathVariable @NotNull @Positive Long id) throws JsonProcessingException {

        String jsonResult = toJson(svc.getOrderById(id), Views.OrderDetail.class);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Создать заказ для пользователя
     */
    @PostMapping
    @PreAuthorize("#userId == authentication.principal.user.id || " +
            "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    public ResponseEntity<String> createOrder(
            @RequestParam @NotNull @Positive Long userId,
            @RequestBody String data) throws JsonProcessingException {

        OrderDto reqOrder = mapper.readValue(data, OrderDto.class);

        String jsonResult = toJson(
                svc.createOrder(reqOrder, userId),
                Views.OrderDetail.class
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Обновить заказ
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN') || @orderAccessService.canAccess(#id, authentication.principal)")
    public ResponseEntity<String> updateOrder(
            @PathVariable @NotNull @Positive Long id,
            @RequestBody String data) throws JsonProcessingException {

        OrderDto reqOrder = mapper.readValue(data, OrderDto.class);

        String jsonResult = toJson(
                svc.updateOrder(reqOrder, id),
                Views.OrderDetail.class
        );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Удалить заказ
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN') || @orderAccessService.canAccess(#id, authentication.principal)")
    public ResponseEntity<String> deleteOrder(
            @PathVariable @NotNull @Positive Long id) {

        svc.deleteOrder(id);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("Order deleted successfully");
    }

    /**
     * Добавить item в заказ
     */
    @PostMapping("/{id}/items")
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN') || @orderAccessService.canAccess(#id, authentication.principal)")
    public ResponseEntity<String> addItemForOrder(
            @PathVariable @NotNull @Positive Long id,
            @RequestBody String data
    ) throws JsonProcessingException {

        ItemDto reqItem = mapper.readValue(data, ItemDto.class);

        String jsonResult = toJson(
                svc.addItemForOrder(reqItem, id),
                Views.OrderDetail.class
        );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    private String toJson(Object data, Class<?> view)
            throws JsonProcessingException {

        return mapper.writerWithView(view)
                .writeValueAsString(data);
    }
}