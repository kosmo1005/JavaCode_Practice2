package com.cool.spring.security.service;

import com.cool.spring.dao.entity.User;
import com.cool.spring.dto.UserAccountResponseDto;
import com.cool.spring.mapper.UserMapper;
import com.cool.spring.security.dao.entity.Role;
import com.cool.spring.security.dao.entity.UserAccount;
import com.cool.spring.security.dao.repo.UserAccountRepository;
import com.cool.spring.dao.repo.UserRepository;
import com.cool.spring.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository repo;
    private final UserRepository userRepo;
    private final UserMapper t;
    private final PasswordEncoder passwordEncoder;

    private static final int MAX_FAILED_ATTEMPTS = 5;

    @Override
    @Transactional(readOnly = true)
    public UserAccount loadUserByUsername(String username) {

        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccount> findForAuthentication(String username) {
        return repo.findByUsername(username);
    }

    @Transactional
    public UserAccount create(
            UserDto userDto,
            String username,
            String email,
            String encodedPassword,
            Set<Role> roles
    ) {

        User user = userRepo.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserAccount account = new UserAccount();
        account.setUser(user);
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(encodedPassword);
        account.setRoles(roles);
        account.setEnabled(true);

        return repo.save(account);
    }

    @Transactional(readOnly = true)
    public boolean userExists(String username, String email) {
        return repo.existsByEmail(email) || repo.existsByUsername(username);
    }

    public void changePassword(Long userId, String password) {
        validatePassword(password);

        UserAccount account = repo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User account not found"));
        account.setPassword(passwordEncoder.encode(password));
    }


    public void grantRole(Long userId, Role role) {

        UserAccount account = getById(userId);
        account.getRoles().add(role);
    }


    public void revokeRole(Long userId, Role role) {

        UserAccount account = getById(userId);
        account.getRoles().remove(role);
    }


    public void lock(Long userId) {

        UserAccount account = getById(userId);
        account.setEnabled(false);
    }


    public void unlock(Long userId) {

        UserAccount account = getById(userId);
        account.setEnabled(true);
    }

    public void loginSucceeded(String username) {

        findForAuthentication(username)
                .ifPresent(account -> account.setFailedLoginAttempts(0));
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void loginFailed(String username) {

        findForAuthentication(username)
                .ifPresent(account -> {
                    int attempts = account.getFailedLoginAttempts() + 1;

                    account.setFailedLoginAttempts(attempts);

                    if (attempts >= MAX_FAILED_ATTEMPTS) {
                        account.setEnabled(false);
                    }

                    repo.save(account);
                });
    }

    @Transactional(readOnly = true)
    public UserAccount getById(Long userId) {
        return repo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User account not found"));
    }

    public UserAccountResponseDto getUserAccountResponse(Long userId) {
        UserAccount account = getById(userId);
        return t.accountToUserResponseDto(account);
    }

    protected void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }
    }

}
