package com.lambda.demo.service;

import com.lambda.demo.domain.User;
import com.lambda.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModernJavaUserServiceImpl implements ModernJavaUserService {
    private final UserRepository userRepository;

    public ModernJavaUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<String> findUserEmailByName(String name) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> name.equals(user.getName()))
                .sorted(Comparator.comparing(User::getId))
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

}
