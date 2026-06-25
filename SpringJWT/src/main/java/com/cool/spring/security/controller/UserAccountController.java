package com.cool.spring.security.controller;

import com.cool.spring.dto.UserAccountResponseDto;
import com.cool.spring.security.dao.entity.Role;
import com.cool.spring.security.dto.ChangePasswordRequestDto;
import com.cool.spring.security.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    /**
     * Получить аккаунт пользователя по id
     */
    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id || " +
                    "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    public ResponseEntity<UserAccountResponseDto> getById(@PathVariable Long id) {

        UserAccountResponseDto account = userAccountService.getUserAccountResponse(id);
        return ResponseEntity.ok(account);
    }

    /**
     * Сменить пароль
     */
    @PatchMapping("/{id}/password")
    @PreAuthorize("#id == authentication.principal.id || " +
                    "hasRole('SUPER_ADMIN')"
    )
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequestDto request) {

        userAccountService.changePassword(id, request.password());
        return ResponseEntity.noContent().build();
    }

    /**
     * Добавление роли пользователю
     */
    @PatchMapping("/{id}/roles/{role}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> grantRole(@PathVariable Long id, @PathVariable Role role) {

        userAccountService.grantRole(id, role);
        return ResponseEntity.noContent().build();
    }

    /**
     * Удаление роли пользователя
     */
    @DeleteMapping("/{id}/roles/{role}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> revokeRole(@PathVariable Long id, @PathVariable Role role) {

        userAccountService.revokeRole(id, role);
        return ResponseEntity.noContent().build();
    }

    /**
     * Ручная блокировка аккаунта пользователя
     */
    @PatchMapping("/{id}/lock")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> lock(@PathVariable Long id) {

        userAccountService.lock(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Ручная разблокировка аккаунта пользователя
     */
    @PatchMapping("/{id}/unlock")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> unlock(@PathVariable Long id) {

        userAccountService.unlock(id);
        return ResponseEntity.noContent().build();
    }
}
