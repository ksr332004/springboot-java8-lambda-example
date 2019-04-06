package com.lambda.demo.service;

import com.lambda.demo.domain.User;
import com.lambda.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ClassicJavaUserServiceImpl implements ClassicJavaUserService {

    private final UserRepository userRepository;

    public ClassicJavaUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> findUserEmailByName(String name) {
        List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = filterByName(allUsers, name);
        sortById(filteredUsers);
        return mapEmails(filteredUsers);
    }

    private List<User> filterByName(List<User> users, String name) {
        List<User> filtered = new ArrayList<>();
        for (User user : users) {
            if (name.equals(user.getName())) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    private void sortById(List<User> users) {
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o1.getId(), o2.getId());
            }
        });
    }

    private List<String> mapEmails(List<User> users) {
        List<String> emails = new ArrayList<>();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }

}