package com.africa.semicolon.controllers;

import com.africa.semicolon.dtos.request.*;
import com.africa.semicolon.dtos.response.*;
import com.africa.semicolon.exceptions.NoteManagementException;
import com.africa.semicolon.services.UserService;
import com.mongodb.internal.bulk.DeleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/note")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            RegisterUserResponse response = userService.register(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            LoginResponse response = userService.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest){
        try{
            LogoutResponse response = userService.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("findUser/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable("username") String username){
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.findByUsername(username)), HttpStatus.OK);
        } catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteUserBy(@RequestBody DeleteUserRequest deleteUserRequest){
        try{
            DeleteResponse deleteResponse = userService.deleteUser(deleteUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, deleteResponse), HttpStatus.OK);
        } catch (NoteManagementException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        try{
            return new ResponseEntity<>(new ApiResponse(true, userService.findAllUsers()), HttpStatus.OK);
        } catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        try{
            UpdateUserResponse response = userService.updateUser(updateUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (NoteManagementException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }



}
