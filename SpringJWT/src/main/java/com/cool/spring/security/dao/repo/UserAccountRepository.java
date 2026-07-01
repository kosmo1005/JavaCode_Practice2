package com.cool.spring.security.dao.repo;

import com.cool.spring.security.dao.entity.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<UserAccount> findByUsername(String username);

    @NonNull
    Optional<UserAccount> findById(@NonNull Long id);

}
