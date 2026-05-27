package com.cool.spring.service;

import com.cool.spring.dao.entity.Order;
import com.cool.spring.dao.entity.User;
import com.cool.spring.dao.repo.OrderRepository;
import com.cool.spring.dao.repo.UserRepository;
import com.cool.spring.dto.ItemDto;
import com.cool.spring.dto.OrderDto;
import com.cool.spring.dto.PageResponse;
import com.cool.spring.mapper.ItemMapper;
import com.cool.spring.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;
    private final UserRepository userRepo;
    private final OrderMapper t;
    private final ItemMapper tItem;

    @Transactional(readOnly = true)
    public PageResponse<OrderDto> getAllOrders(Pageable pageable, Long userId) {
        Page<OrderDto> result = repo.findAllByUserId(userId, pageable).map(t::toDto);

        return PageResponse.from(result);
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return t.toDto(order);
    }

    @Transactional
    public OrderDto createOrder(OrderDto data, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = t.toEntityForCreate(data);
        user.addOrder(order);

        return t.toDto(repo.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found. Delete impossible"));

        repo.delete(order);

    }

    @Transactional
    public OrderDto updateOrder(OrderDto data, Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        if (data.getStatus() != null) {
            order.setStatus(data.getStatus());
        }
        if (data.getAmount() != null) {
            order.setAmount(data.getAmount());
        }
        if (data.getItems() != null) {
            order.replaceItems(tItem.toEntityList(data.getItems()));
        }

        return t.toDto(order);
    }

    @Transactional
    public OrderDto addItemForOrder(ItemDto data, Long orderId) {
        Order order = repo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.addItem(tItem.toEntity(data));

        return t.toDto(order);
    }
}
