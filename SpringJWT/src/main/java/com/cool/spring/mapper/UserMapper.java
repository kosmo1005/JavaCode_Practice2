package com.cool.spring.mapper;

import com.cool.spring.dao.entity.User;
import com.cool.spring.security.dao.entity.UserAccount;
import com.cool.spring.dto.UserDto;
import com.cool.spring.dto.UserAccountResponseDto;
import com.cool.spring.security.dto.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final OrderMapper orderMapper;

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .orders(orderMapper.toDtoList(user.getOrders()))
                .build();
    }

    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return user;
    }

    public User toEntityForCreate(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return user;
    }

    public UserDto registerUserDtoToRequestDto(RegisterRequestDto rrd) {
        if (rrd == null) {
            return null;
        }

        return UserDto.builder()
                .name(rrd.name())
                .email(rrd.email())
                .orders(new ArrayList<>())
                .build();
    }

    public UserAccountResponseDto accountToUserResponseDto(UserAccount acc) {
        if (acc == null) {
            return null;
        }

        return UserAccountResponseDto.builder()
                .id(acc.getId())
                .name(acc.getUsername())
                .email(acc.getEmail())
                .build();
    }

}
