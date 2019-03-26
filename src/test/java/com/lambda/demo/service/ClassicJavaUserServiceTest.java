package com.lambda.demo.service;

import com.lambda.demo.domain.User;
import com.lambda.demo.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassicJavaUserServiceTest {

    @Autowired
    ClassicJavaUserService classicJavaUserService;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.save(new User("TEST1", "test1@test1.com"));
        userRepository.save(new User("TEST2", "test2@test2.com"));
        userRepository.save(new User("TEST3", "test3@test3.com"));
        userRepository.save(new User("TEST1", "test1@test2.com"));
        userRepository.save(new User("TEST2", "test2@test3.com"));
    }

    @Test
    public void classicJavaUserTest() {
        List<String> emails = classicJavaUserService.findUserEmailByName("TEST1");
        Assert.assertEquals(emails, Arrays.asList("test1@test1.com", "test1@test2.com"));
    }

}
