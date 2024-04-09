package com.africa.semicolon.services;

import com.africa.semicolon.data.model.User;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.response.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.africa.semicolon.utils.Mapper.mapRegisterUser;
import static com.africa.semicolon.utils.Mapper.mapRegisterUserResponse;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        User user = mapRegisterUser(registerUserRequest);
        User savedUser = userRepository.save(user);
        return mapRegisterUserResponse(savedUser);
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }
}
