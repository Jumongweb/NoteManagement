package com.africa.semicolon.services;

import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
    }

    @Test
    public void testThatUserServiceCanSaveUser(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setFirstName("firstName");
        registerUserRequest.setLastName("lastName");
        userService.register(registerUserRequest);
        System.out.println("i can get here");
        assertEquals(1, userService.countUsers());
    }

}