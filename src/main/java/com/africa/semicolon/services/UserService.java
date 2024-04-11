package com.africa.semicolon.services;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import com.africa.semicolon.dtos.request.*;
import com.africa.semicolon.dtos.response.*;

import java.net.ContentHandler;
import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    Long countUsers();

    User findByUsername(String username);

    List<User> findAllUsers();

    DeleteResponse deleteUser(DeleteUserRequest deleteUserRequest);

    UpdateUserResponse updateUser(String username, String password, UpdateUserRequest update);

    LoginResponse login(LoginRequest loginRequest);

    LogoutResponse logout(LogoutRequest logoutRequest);

    AddNoteResponse addNote(AddNoteRequest addNoteRequest);

    Note findNoteBy(String username1, String title1);

    List<Note> findAllNotesBelongingToUser(String username1);
}
