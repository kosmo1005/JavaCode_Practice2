package com.cool.spring.security.service;

import com.cool.spring.security.dao.entity.Role;
import com.cool.spring.dto.UserDto;
import com.cool.spring.dto.UserAccountResponseDto;
import com.cool.spring.security.dao.entity.UserAccount;
import com.cool.spring.security.dto.LoginRequestDto;
import com.cool.spring.security.dto.RegisterRequestDto;
import com.cool.spring.security.dto.TokenResponseDto;
import com.cool.spring.mapper.UserMapper;
import com.cool.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserService userService;
    private final UserAccountService userAccountService;
    private final UserMapper t;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Transactional
    public UserAccountResponseDto register(RegisterRequestDto regDto) {
        userAccountService.validatePassword(regDto.password());

        if (userAccountService.userExists(regDto.name(), regDto.email())) {
            throw new IllegalArgumentException("User already exists");
        }

        UserDto user = userService.createUser(t.registerUserDtoToRequestDto(regDto));

        return t.accountToUserResponseDto(
                userAccountService.create(
                        user,
                        regDto.name(),
                        regDto.email(),
                        passwordEncoder.encode(regDto.password()),
                        Set.of(Role.USER)
                ));
    }


    @Transactional
    public TokenResponseDto login(LoginRequestDto request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()));

            UserAccount principal = (UserAccount) authentication.getPrincipal();

            userAccountService.loginSucceeded(principal.getUsername());

            String accessToken = jwtService.generateAccessToken(principal);
            String refreshToken = jwtService.generateRefreshToken(principal);

            return new TokenResponseDto(accessToken, refreshToken);

        } catch (BadCredentialsException e) {

            userAccountService.loginFailed(request.username());

            throw e;
        }
    }

    @Transactional(readOnly = true)
    public TokenResponseDto refresh(String refreshToken) {

        String username = jwtService.extractUserName(refreshToken);
        UserAccount user = userAccountService.findForAuthentication(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (!user.isEnabled()) {
            throw new DisabledException("К сожалению, User disabled");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return new TokenResponseDto(accessToken, newRefreshToken);
    }

}
