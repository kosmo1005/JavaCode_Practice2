package com.cool.spring.security.service;

import com.cool.spring.dao.repo.OrderRepository;
import com.cool.spring.security.dao.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAccessService {

    private final OrderRepository orderRepo;

    public boolean canAccess(Long orderId, UserAccount principal) {
        Long userId = principal.getUser().getId();
        return orderRepo.existsByIdAndUserId(orderId, userId);
    }

}