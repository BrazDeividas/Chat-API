package com.api.chat.configuration;

import com.api.chat.entity.User;
import com.api.chat.entity.role.RoleName;
import com.api.chat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            try{
                if(userRepository.findByUsername("petras") == null);
            }
            catch(Exception ignored) {
                User user = new User("petras", "petras1", RoleName.ROLE_USER);
                userRepository.add(user);
            }
        };
    }
}
