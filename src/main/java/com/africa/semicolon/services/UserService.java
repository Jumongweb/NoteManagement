package com.africa.semicolon.services;

import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
}
