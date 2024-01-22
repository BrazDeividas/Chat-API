package com.api.chat.tests;

import com.api.chat.entity.User;
import com.api.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndRetrieveUser() {
        User user = new User("John Doe", "johnnyboy1");
        userRepository.add(user);

        User retrievedUser = userRepository.findById(user.getId());

        assertEquals(retrievedUser, user);
    }
}
