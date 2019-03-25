package com.lambda.demo.service;

import com.lambda.demo.domain.User;
import com.lambda.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ClassicJavaUserServiceImpl implements ClassicJavaUserService {

    private UserRepository userRepository;

    public ClassicJavaUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findUserEmailByName(String name) {
        return null;
    }

    private List<User> filter(List<User> users, String name) {
        List<User> filtered = new ArrayList<>();
        for (User user : users) {
            if (name.equals(user.getName())) {
                filtered.add(user);
            }
        }
        return filtered;
    }

}