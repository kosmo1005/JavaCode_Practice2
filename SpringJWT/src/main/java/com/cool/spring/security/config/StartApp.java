package com.cool.spring.security.config;

import com.cool.spring.security.dao.entity.Role;
import com.cool.spring.security.dao.entity.UserAccount;
import com.cool.spring.security.dao.repo.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class StartApp {

    @Bean
    CommandLineRunner init(
            UserAccountRepository repo,
            PasswordEncoder encoder
    ) {

        return args -> {

            if (repo.existsByUsername("superadmin")) {
                return;
            }

            UserAccount acc = new UserAccount();

            acc.setUsername("superadmin");
            acc.setPassword(encoder.encode("super123"));

            acc.setRoles(Set.of(Role.SUPER_ADMIN));
            repo.save(acc);
        };
    }
}
