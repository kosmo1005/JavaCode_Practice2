package com.cool.spring.mapper;

import com.cool.spring.dao.entity.Item;
import com.cool.spring.dao.entity.Order;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ItemMapper itemMapper;

    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        return OrderDto.builder()
                .id(order.getId())
                .items(itemMapper.toDtoList(order.getItems()))
                .amount(order.getAmount())
                .status(order.getStatus())
                .user(order.getUser() == null
                        ? null
                        : UserDto.builder()
                        .id(order.getUser().getId())
                        .name(order.getUser().getName())
                        .email(order.getUser().getEmail())
                        .build())
                .build();
    }

    public Order toEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        }

        Order order = new Order();

        order.setId(dto.getId());
        order.setAmount(dto.getAmount());
        order.setStatus(dto.getStatus());
        List<Item> items = itemMapper.toEntityList(dto.getItems());

        if (items != null) {
            items.forEach(order::addItem);
        }

        return order;
    }

    public Order toEntityForCreate(OrderDto dto) {
        if (dto == null) {
            return null;
        }

        Order order = new Order();

        order.setAmount(dto.getAmount());
        order.setStatus(dto.getStatus());
        List<Item> items = itemMapper.toEntityList(dto.getItems());

        if (items != null) {
            items.forEach(order::addItem);
        }

        return order;
    }

    public List<OrderDto> toDtoList(List<Order> orders) {
        if (orders == null) {
            return List.of();
        }
        return orders.stream()
                .map(this::toDto)
                .toList();
    }

    public List<Order> toEntityList(List<OrderDto> orders) {
        if (orders == null) {
            return List.of();
        }
        return orders.stream()
                .map(this::toEntity)
                .toList();
    }
}
