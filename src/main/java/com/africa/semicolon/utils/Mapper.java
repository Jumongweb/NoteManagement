package com.africa.semicolon.utils;

import com.africa.semicolon.data.model.User;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.request.UpdateUserRequest;
import com.africa.semicolon.dtos.response.DeleteResponse;
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

    public static DeleteResponse mapDeleteUser(User user) {
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Deleted successfully");
        return response;
    }

    public static User mapUpdateUser(UpdateUserRequest updateUserRequest) {
        User user = new User();
        user.setUsername(updateUserRequest.getUsername());
        user.setPassword(updateUserRequest.getPassword());
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        return user;
    }

    public static RegisterUserResponse mapUpdateUserResponse(User user) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage("Updated successfully");
        return response;
    }
}
