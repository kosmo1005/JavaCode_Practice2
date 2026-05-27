package com.cool.spring.controller;

import com.cool.spring.dto.PageResponse;
import com.cool.spring.dto.UserDto;
import com.cool.spring.service.UserService;
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
@Validated
public class UserController {

    private final UserService svc;
    private final ObjectMapper mapper;

    public UserController (UserService svc, ObjectMapper mapper){
        this.svc = svc;
        this.mapper = mapper;
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Получить список пользователей
     */
    @GetMapping
    public ResponseEntity<String> getAllUsers(
            @PageableDefault(size = 20) Pageable pageable
    ) throws JsonProcessingException {
        PageResponse<UserDto> pageResponse = svc.getAllUsers(pageable);
        String jsonResult = toJson(pageResponse, Views.Summary.class);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Получить расширенную информацию о пользователе по id
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable @NotNull @Positive Long id) throws JsonProcessingException {
        String jsonResult = toJson(svc.getUserById(id), Views.UserDetail.class);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Создать пользователя
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody String data) throws JsonProcessingException {
        UserDto reqUser = mapper.readValue(data, UserDto.class);
        String jsonResult = toJson(svc.createUser(reqUser), Views.Summary.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Обновить данные о пользователе по id
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable @NotNull @Positive Long id,
            @RequestBody String data
    ) throws JsonProcessingException {
        UserDto reqUser = mapper.readValue(data, UserDto.class);
        String jsonResult = toJson(svc.updateUser(reqUser, id), Views.UserDetail.class);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResult);
    }

    /**
     * Удалить пользователя по id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(svc.deleteUser(id));
    }

    private String toJson(Object data, Class<?> view)
            throws JsonProcessingException {

        return mapper.writerWithView(view)
                .writeValueAsString(data);
    }
}
