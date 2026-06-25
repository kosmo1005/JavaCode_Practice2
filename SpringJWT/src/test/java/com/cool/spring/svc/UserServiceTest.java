package com.cool.spring.svc;

import com.cool.spring.dao.entity.User;
import com.cool.spring.dao.repo.UserRepository;
import com.cool.spring.data.DataProvider;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.dto.PageResponse;
import com.cool.spring.dto.UserDto;
import com.cool.spring.mapper.ItemMapper;
import com.cool.spring.mapper.OrderMapper;
import com.cool.spring.mapper.UserMapper;
import com.cool.spring.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repo;

    private final ItemMapper tItem = new ItemMapper();
    private final OrderMapper tOrder = new OrderMapper(tItem);
    private final UserMapper t = new UserMapper(tOrder);

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(repo, t);
    }

    @Test
    void getAllUsersTest() {

        Pageable pageable = PageRequest.of(0, 20);

        when(repo.findAll(any(Pageable.class)))
                .thenReturn(DataProvider.getUserEntityPage());

        PageResponse<UserDto> result =
                userService.getAllUsers(pageable);

        assertNotNull(result);

        assertEquals(1, result.content().size());

        assertValidUser(result.content().get(0));

        assertEquals(0, result.page());
        assertEquals(20, result.size());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());

        verify(repo)
                .findAll(any(Pageable.class));
    }

    @Test
    void getUserByIdTest() {

        when(repo.findById(1L))
                .thenReturn(Optional.of(DataProvider.getValidUserEntity()));

        UserDto result = userService.getUserById(1L);

        assertValidUser(result);

        verify(repo)
                .findById(1L);
    }

    @Test
    void getUserByIdTest_whenUserNotFound() {

        when(repo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUserById(1L));

        verify(repo)
                .findById(1L);
    }

    @Test
    void createUserTest() {

        UserDto createUserDto = DataProvider.getUserDtoForCreate();

        assertNull(createUserDto.getId());

        when(repo.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(2L);

                    return user;
                });

        UserDto result = userService.createUser(createUserDto);

        verify(repo)
                .save(any(User.class));

        assertNotNull(result);

        assertEquals(2L, result.getId());
        assertEquals("Alex", result.getName());
        assertEquals("alex@test.com", result.getEmail());
    }

    @Test
    void deleteUserTest() {

        String result = userService.deleteUser(1L);

        assertEquals(
                "User 1 deleted successfully",
                result
        );

        verify(repo)
                .deleteById(1L);
    }

    @Test
    void deleteUserTest_whenUserNotFound() {

        doThrow(new EmptyResultDataAccessException(1))
                .when(repo)
                .deleteById(1L);

        assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUser(1L));

        verify(repo)
                .deleteById(1L);
    }

    @Test
    void updateUserTest() {

        User user = DataProvider.getValidUserEntity();

        when(repo.findById(1L))
                .thenReturn(Optional.of(user));

        UserDto result = userService.updateUser(DataProvider.getUserDtoForUpdate(), 1L);

        assertNotNull(result);

        assertEquals(1L, result.getId());
        assertEquals("Updated Alex", result.getName());
        assertEquals("updated@test.com", result.getEmail());

        verify(repo)
                .findById(1L);
    }

    private void assertValidUser(UserDto userDto) {

        assertNotNull(userDto);

        assertEquals(1L, userDto.getId());
        assertEquals("Alex", userDto.getName());
        assertEquals("alex@test.com", userDto.getEmail());

        assertNotNull(userDto.getOrders());

        assertEquals(1, userDto.getOrders().size());

        OrderDto order = userDto.getOrders().get(0);

        assertEquals(1L, order.getId());
        assertEquals(100.0, order.getAmount());
        assertEquals("CREATED", order.getStatus());
    }
}
