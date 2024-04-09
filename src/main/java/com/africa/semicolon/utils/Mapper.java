package com.africa.semicolon.utils;

import com.africa.semicolon.data.model.User;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.response.RegisterUserResponse;

public class Mapper {
    public static User mapRegisterUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        return user;
    }

    public static RegisterUserResponse mapRegisterUserResponse(User user) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage("Registered successfully");
        return response;
    }
}
