package com.cool.spring.svc;

import com.cool.spring.dao.entity.Order;
import com.cool.spring.dao.entity.User;
import com.cool.spring.dao.repo.OrderRepository;
import com.cool.spring.dao.repo.UserRepository;
import com.cool.spring.data.DataProvider;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.dto.PageResponse;
import com.cool.spring.mapper.ItemMapper;
import com.cool.spring.mapper.OrderMapper;
import com.cool.spring.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repo;

    @Mock
    private UserRepository userRepo;

    private final ItemMapper tItem = new ItemMapper();
    private final OrderMapper t = new OrderMapper(tItem);

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(repo, userRepo, t, tItem);
    }


    @Test
    void getAllOrdersTest() {

        Pageable pageable = PageRequest.of(0, 20);

        when(repo.findAllByUserId(1L, pageable))
                .thenReturn(DataProvider.getOrderEntityPage());


        PageResponse<OrderDto> result =
                orderService.getAllOrders(pageable, 1L);

        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertValidOrder(result.content().get(0));

        assertEquals(0, result.page());
        assertEquals(20, result.size());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());

        verify(repo)
                .findAllByUserId(anyLong(), any(Pageable.class));
    }

    @Test
    void getOrderByIdTest() {

        when(repo.findById(1L))
                .thenReturn(Optional.of(DataProvider.getValidOrderEntity()));

        OrderDto result = orderService.getOrderById(1L);

        assertValidOrder(result);

        verify(repo)
                .findById(1L);
    }

    @Test
    void getOrderByIdTest_whenOrderNotFound() {

        when(repo.findById(1L))
                .thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class,
                () -> orderService.getOrderById(1L));

        verify(repo)
                .findById(1L);
    }

    @Test
    void createOrderTest() {

        User user = DataProvider.getValidUserEntity();

        OrderDto createOrderDto = DataProvider.getOrderDtoForCreate();

        assertNull(createOrderDto.getId());

        when(userRepo.findById(1L))
                .thenReturn(Optional.of(user));

        when(repo.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(101L);

                    return order;
                });

        OrderDto result = orderService.createOrder(createOrderDto, 1L);

        verify(userRepo).findById(1L);

        verify(repo).save(any(Order.class));

        assertNotNull(result);

        assertEquals(2, user.getOrders().size());
        assertEquals(101L, result.getId());
        assertEquals(150.75, result.getAmount());
        assertEquals("CREATED", result.getStatus());
        assertEquals(2, result.getItems().size());
        assertEquals(10L, result.getItems().get(0).getId());
        assertEquals("Milk", result.getItems().get(0).getName());
        assertEquals(100.50, result.getItems().get(0).getPrice());

        assertNotNull(result.getUser());
        assertEquals(1L, result.getUser().getId());
    }

    @Test
    void deleteOrderTest() {

        Order order = DataProvider.getValidOrderEntity();

        when(repo.findById(1L))
                .thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(repo)
                .findById(1L);

        verify(repo)
                .delete(order);
    }

    @Test
    void addItemForOrderTest_whenOrderNotFound() {

        when(repo.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.addItemForOrder(
                        DataProvider.getItem1(),
                        1L
                ));

        verify(repo)
                .findById(1L);
    }

    private void assertValidOrder(OrderDto orderDto) {
        assertNotNull(orderDto);
        assertEquals(1L, orderDto.getId());
        assertEquals(150.75, orderDto.getAmount());
        assertEquals("CREATED", orderDto.getStatus());

        assertNotNull(orderDto.getUser());
        assertEquals(1L, orderDto.getUser().getId());
        assertEquals("Alex", orderDto.getUser().getName());
        assertEquals("alex@test.com", orderDto.getUser().getEmail());

        assertEquals(2, orderDto.getItems().size());
        assertEquals(10L, orderDto.getItems().get(0).getId());
        assertEquals("Milk", orderDto.getItems().get(0).getName());
        assertEquals(100.50, orderDto.getItems().get(0).getPrice());
    }
}
