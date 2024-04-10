package com.africa.semicolon.services;

import com.africa.semicolon.data.model.User;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.request.UpdateUserRequest;
import com.africa.semicolon.dtos.response.DeleteResponse;
import com.africa.semicolon.dtos.response.RegisterUserResponse;
import com.africa.semicolon.dtos.response.UpdateUserResponse;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    Long countUsers();

    User findByUsername(String username);

    List<User> findAllUsers();

    DeleteResponse deleteUser(String username);

    UpdateUserResponse updateUser(String username, String password, UpdateUserRequest update);
}
