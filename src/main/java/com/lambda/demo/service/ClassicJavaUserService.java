package com.lambda.demo.service;

import com.lambda.demo.domain.User;

import java.util.List;

public interface ClassicJavaUserService {

    List<User> findUserEmailByName(String name);

}