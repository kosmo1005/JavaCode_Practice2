package com.cool.spring.controller;

import com.cool.spring.data.DataProvider;
import com.cool.spring.dto.UserDto;
import com.cool.spring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void getAllUsersTest() throws Exception {

        when(userService.getAllUsers(any(Pageable.class)))
                .thenReturn(DataProvider.getUserPage());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))

                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Alex"))

                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(userService)
                .getAllUsers(any(Pageable.class));
    }

    @Test
    void getUserByIdTest() throws Exception {

        when(userService.getUserById(1L))
                .thenReturn(DataProvider.getValidUserDto());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@test.com"))

                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders.length()").value(1))
                .andExpect(jsonPath("$.orders[0].id").value(1))
                .andExpect(jsonPath("$.orders[0].amount").value(100.0));

        verify(userService)
                .getUserById(1L);
    }

    @Test
    void getUserByIdTest_whenIdInvalid() throws Exception {

        mockMvc.perform(get("/users/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserTest() throws Exception {

        when(userService.createUser(any(UserDto.class)))
                .thenReturn(DataProvider.getValidUserDto());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                DataProvider.getUserDtoForCreate()
                        )))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alex"))

                .andExpect(jsonPath("$.email").doesNotExist());

        verify(userService)
                .createUser(any(UserDto.class));
    }

    @Test
    void updateUserTest() throws Exception {

        when(userService.updateUser(any(UserDto.class), eq(1L)))
                .thenReturn(DataProvider.getValidUserDto());

        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                DataProvider.getValidUserDto()
                        )))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@test.com"));

        verify(userService)
                .updateUser(any(UserDto.class), eq(1L));
    }

    @Test
    void deleteUserTest() throws Exception {

        when(userService.deleteUser(1L))
                .thenReturn("User deleted successfully");

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));

        verify(userService)
                .deleteUser(1L);
    }
}
