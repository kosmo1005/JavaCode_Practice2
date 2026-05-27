package com.cool.spring.controller;

import com.cool.spring.data.DataProvider;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.service.OrderService;
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

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;


    @Test
    void getAllOrdersTest() throws Exception {

        when(orderService.getAllOrders(any(Pageable.class), eq(1L)))
                .thenReturn(DataProvider.getOrderPage());

        mockMvc.perform(get("/orders")
                        .param("userId", "1"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))

                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].amount").value(100.0))

                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(orderService)
                .getAllOrders(any(Pageable.class), eq(1L));
    }

    @Test
    void getOrderByIdTest() throws Exception {

        when(orderService.getOrderById(1L))
                .thenReturn(DataProvider.getValidOrderDto());

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("CREATED"));

        verify(orderService).getOrderById(1L);
    }

    @Test
    void getOrderByIdTest_whenIdIsInvalid() throws Exception {

        mockMvc.perform(get("/orders/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrderTest() throws Exception {

        when(orderService.createOrder(any(OrderDto.class), eq(1L)))
                .thenReturn(DataProvider.getResponseOrderDto());

        mockMvc.perform(post("/orders")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataProvider.getOrderDtoForCreate())))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.amount").value(150.75))
                .andExpect(jsonPath("$.status").value("CREATED"))

                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(10))
                .andExpect(jsonPath("$.items[0].name").value("Milk"))
                .andExpect(jsonPath("$.items[0].price").value(100.50))
                .andExpect(jsonPath("$.items[1].id").value(11))
                .andExpect(jsonPath("$.items[1].name").value("Bread"))
                .andExpect(jsonPath("$.items[1].price").value(50.25))

                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.name").value("Alex"));


        verify(orderService)
                .createOrder(any(OrderDto.class), eq(1L));
    }

    @Test
    void createOrderTest_whenUserIdIsInvalid() throws Exception {

        mockMvc.perform(post("/orders")
                        .param("userId", "-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataProvider.getOrderDtoForCreate())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrderTest() throws Exception {

        when(orderService.updateOrder(any(OrderDto.class), eq(100L)))
                .thenReturn(DataProvider.getResponseOrderDto());

        mockMvc.perform(patch("/orders/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataProvider.getOrderDtoForCreate())))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.amount").value(150.75))
                .andExpect(jsonPath("$.status").value("CREATED"))

                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(10))
                .andExpect(jsonPath("$.items[0].name").value("Milk"))
                .andExpect(jsonPath("$.items[0].price").value(100.50))
                .andExpect(jsonPath("$.items[1].id").value(11))
                .andExpect(jsonPath("$.items[1].name").value("Bread"))
                .andExpect(jsonPath("$.items[1].price").value(50.25))

                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.name").value("Alex"));


        verify(orderService)
                .updateOrder(any(OrderDto.class), eq(100L));
    }

    @Test
    void deleteOrderTest() throws Exception {

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted successfully"));

        verify(orderService).deleteOrder(1L);
    }
}
