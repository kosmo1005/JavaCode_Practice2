package com.cool.spring.controller;

import com.cool.spring.dto.PageResponse;
import com.cool.spring.dto.UserDto;
import com.cool.spring.service.UserService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService svc;

    /**
     * Получить список пользователей
     */
    @GetMapping
    @JsonView(Views.Summary.class)
    public ResponseEntity<PageResponse<UserDto>> getAllOrders(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(svc.getAllUsers(pageable));
    }

    /**
     * Получить расширенную информацию о пользователе по id
     */
    @GetMapping("/{id}")
    @JsonView(Views.UserDetail.class)
    public ResponseEntity<UserDto> getUserById(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(svc.getUserById(id));
    }

    /**
     * Создать пользователя
     */
    @PostMapping
    @JsonView(Views.Summary.class)
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(svc.createUser(data));
    }

    /**
     * Обновить данные о пользователе по id
     */
    @PatchMapping("/{id}")
    @JsonView(Views.UserDetail.class)
    public ResponseEntity<UserDto> updateUser(
            @PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid UserDto data
    ) {
        return ResponseEntity.ok(svc.updateUser(data, id));
    }

    /**
     * Удалить пользователя по id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(svc.deleteUser(id));
    }
}
