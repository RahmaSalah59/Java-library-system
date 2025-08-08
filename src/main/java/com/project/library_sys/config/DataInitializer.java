package com.project.library_sys.config;

import com.project.library_sys.model.AppUser;
import com.project.library_sys.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if no users exist
        if (userRepository.count() == 0) {
            AppUser adminUser = new AppUser(
                "admin", 
                passwordEncoder.encode("admin123"), 
                "ROLE_ADMIN"
            );
            userRepository.save(adminUser);
            
            AppUser regularUser = new AppUser(
                "user", 
                passwordEncoder.encode("user123"), 
                "ROLE_USER"
            );
            userRepository.save(regularUser);
            
            System.out.println("=== DEFAULT USERS CREATED ===");
            System.out.println("Admin User: username=admin, password=admin123");
            System.out.println("Regular User: username=user, password=user123");
            System.out.println("===============================");
        }
    }
}
