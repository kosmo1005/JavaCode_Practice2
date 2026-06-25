package com.cool.spring.service;

import com.cool.spring.dao.entity.User;
import com.cool.spring.dao.repo.UserRepository;
import com.cool.spring.dto.PageResponse;
import com.cool.spring.dto.UserDto;
import com.cool.spring.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final UserMapper t;

    @Transactional(readOnly = true)
    public PageResponse<UserDto> getAllUsers(Pageable pageable) {
        Page<UserDto> result = repo.findAll(pageable).map(t::toDto);

        return PageResponse.from(result);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return t.toDto(user);
    }

    @Transactional
    public UserDto createUser(UserDto data) {
        User user = t.toEntityForCreate(data);
        User saved = repo.save(user);
        return t.toDto(saved);
    }

    @Transactional
    public String deleteUser(Long id) {
        try {
            repo.deleteById(id);
            return  "User " + id + " deleted successfully";
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User not found. Delete impossible");
        }
    }

    @Transactional
    public UserDto updateUser(UserDto data, Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (data.getName() != null) {
            user.setName(data.getName());
        }
        if (data.getEmail() != null) {
            user.setEmail(data.getEmail());
        }

        return t.toDto(user);
    }
}
