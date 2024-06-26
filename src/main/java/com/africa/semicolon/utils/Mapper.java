package com.africa.semicolon.utils;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import com.africa.semicolon.dtos.request.AddNoteRequest;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.request.UpdateUserRequest;
import com.africa.semicolon.dtos.response.*;

import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User mapRegisterUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setUsername(registerUserRequest.getUsername().toLowerCase());
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

    public static LoginResponse mapLoginResponse(User user) {
        LoginResponse response = new LoginResponse();
        response.setMessage("Login successful");
        return response;
    }

    public static LogoutResponse mapLogoutResponse(User user) {
        LogoutResponse response = new LogoutResponse();
        response.setMessage("Logout successful");
        return response;
    }

    public static Note mapAddNote(AddNoteRequest addNoteRequest){
        Note note = new Note();
        note.setTitle(addNoteRequest.getTitle());
        note.setContent(addNoteRequest.getContent());
        note.setUsername(addNoteRequest.getUsername());
        return note;
    }

    public static AddNoteResponse mapAddNoteResponse(Note note){
        AddNoteResponse response = new AddNoteResponse();
        response.setMessage("Note Added successfully");
        return response;
    }

    public static DeleteNoteResponse mapDeleteNoteResponse(Note note){
        DeleteNoteResponse response = new DeleteNoteResponse();
        response.setMessage("Note Deleted successfully");
        return response;
    }

    public static UpdateNoteResponse mapUpdateNoteResponse(Note note){
        UpdateNoteResponse response = new UpdateNoteResponse();
        response.setMessage("Note Updated successfully");
        return response;
    }

    public static ShareNoteResponse mapSharedNoteResponse(){
        ShareNoteResponse response = new ShareNoteResponse();
        response.setMessage("Note Shared successfully");
        return response;
    }
}
