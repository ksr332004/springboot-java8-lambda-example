package com.lambda.demo.repository;

import com.lambda.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private User user;

    @Before
    public void serUp() {
        user = new User();
        user.setName("TEST");
        user.setEmail("test@test.com");
    }

    @Test
    public void crudTest() {
        // Create Date
        User createUser = userRepository.save(user);
        Assert.assertEquals(user.getName(), createUser.getName());

        // Read Data
        List<User> readUserList = userRepository.findAll();
        Assert.assertNotNull(readUserList);

        // Update Data
        user.setName("TEST2");
        User updateUser = userRepository.save(user);
        Assert.assertEquals(user.getId(), updateUser.getId());

        // Delete Date
        userRepository.delete(user);
        List<User> deleteUserList = userRepository.findAll();
        Assert.assertEquals(deleteUserList.size(), 0);
    }
}