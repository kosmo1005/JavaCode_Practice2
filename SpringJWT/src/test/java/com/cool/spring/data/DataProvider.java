package com.cool.spring.data;

import com.cool.spring.dao.entity.Item;
import com.cool.spring.dao.entity.Order;
import com.cool.spring.dao.entity.User;
import com.cool.spring.dto.ItemDto;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.dto.PageResponse;
import com.cool.spring.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {


    //--------------Orders dto data--------------------------

    public static PageResponse<OrderDto> getOrderPage() {
        return new PageResponse<>(
                List.of(getValidOrderDto()),
                0,
                20,
                1,
                1
        );
    }

    public static OrderDto getValidOrderDto() {
        return OrderDto.builder()
                .id(1L)
                .amount(100.0)
                .status("CREATED")
                .build();
    }

    public static OrderDto getOrderDtoForCreate() {
        return OrderDto.builder()
                .amount(150.75)
                .status("CREATED")
                .items(List.of(getItem1(), getItem2()))
                .user(getUserDto())
                .build();
    }

    public static OrderDto getResponseOrderDto() {
        return OrderDto.builder()
                .id(100L)
                .amount(150.75)
                .status("CREATED")
                .items(List.of(getItem1(), getItem2()))
                .user(getUserDto())
                .build();
    }


    public static UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .name("Alex")
                .email("alex@test.com")
                .build();
    }

    public static ItemDto getItem1() {
        return ItemDto.builder()
                .id(10L)
                .name("Milk")
                .price(100.50)
                .build();
    }

    public static ItemDto getItem2() {
        return ItemDto.builder()
                .id(11L)
                .name("Bread")
                .price(50.25)
                .build();
    }


    //--------------Users dto data--------------------------

    public static PageResponse<UserDto> getUserPage() {
        return new PageResponse<>(
                List.of(getValidUserDto()),
                0,
                20,
                1,
                1
        );
    }

    public static UserDto getValidUserDto() {
        List<OrderDto> orderDtolist = new ArrayList<>();
        orderDtolist.add(getValidOrderDto());
        return UserDto.builder()
                .id(1L)
                .name("Alex")
                .email("alex@test.com")
                .orders(orderDtolist)
                .build();
    }

    public static UserDto getUserDtoForUpdate() {
        return UserDto.builder()
                .name("Updated Alex")
                .email("updated@test.com")
                .build();
    }

    public static UserDto getUserDtoForCreate() {
        return UserDto.builder()
                .name("Alex")
                .email("alex@test.com")
                .build();
    }

    //--------------Orders entity data--------------------------

    public static Page<Order> getOrderEntityPage() {
        return new PageImpl<>(
                List.of(getValidOrderEntity()),
                PageRequest.of(0, 20),
                1
        );
    }

    public static Order getValidOrderEntity() {
        Order order = new Order();
        order.setId(1L);
        order.setAmount(150.75);
        order.setStatus("CREATED");
        order.setItems(List.of(getItemEntity1(), getItemEntity2()));
        order.setUser(getValidUserEntity());
        return order;
    }

    public static Item getItemEntity1() {
        Item item = new Item();
        item.setId(10L);
        item.setName("Milk");
        item.setPrice(100.50);
        return item;
    }

    public static Item getItemEntity2() {
        Item item = new Item();
        item.setId(11L);
        item.setName("Bread");
        item.setPrice(50.25);
        return item;
    }

    public static User getValidUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setName("Alex");
        user.setEmail("alex@test.com");
        List<Order> orderlist = new ArrayList<>();
        orderlist.add(getOrderEntity());
        user.setOrders(orderlist);
        return user;
    }

    public static Order getOrderEntity() {
        Order order = new Order();
        order.setId(1L);
        order.setAmount(100.0);
        order.setStatus("CREATED");
        return order;
    }


    //--------------Users entity data--------------------------

    public static Page<User> getUserEntityPage() {
        return new PageImpl<>(
                List.of(getValidUserEntity()),
                PageRequest.of(0, 20),
                1
        );
    }
}
