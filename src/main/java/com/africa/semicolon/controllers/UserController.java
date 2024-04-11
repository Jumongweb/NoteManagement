package com.africa.semicolon.controllers;

import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.response.RegisterUserResponse;
import com.africa.semicolon.dtos.response.UserApiResponse;
import com.africa.semicolon.exceptions.NoteManagementException;
import com.africa.semicolon.services.UserService;
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
            return new ResponseEntity<>(new UserApiResponse(true, response), HttpStatus.CREATED);
        } catch (NoteManagementException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
