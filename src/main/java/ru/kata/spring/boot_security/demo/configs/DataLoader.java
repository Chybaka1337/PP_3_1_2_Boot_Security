package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    @Value("${admin.username}")
    private String userName;
    @Value("${admin.password}")
    private String password;

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Autowired
    public DataLoader(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (roleRepo.findByRoleName(ROLE_ADMIN).isEmpty()) {
            roleRepo.save(new Role(ROLE_ADMIN));
        }

        if (roleRepo.findByRoleName(ROLE_USER).isEmpty()) {
            roleRepo.save(new Role(ROLE_USER));
        }

        if (userRepo.findByRole(ROLE_ADMIN).isEmpty()) {
            User admin = new User(userName, password);
            admin.setPassword(encoder.encode(admin.getPassword()));
            admin.setRoles(new HashSet<>(roleRepo.findAll()));
            userRepo.save(admin);
        }
    }
}
