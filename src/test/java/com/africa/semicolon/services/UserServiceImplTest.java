package com.africa.semicolon.services;

import com.africa.semicolon.data.model.User;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.LoginRequest;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.request.UpdateUserRequest;
import com.africa.semicolon.exceptions.InvalidPasswordException;
import com.africa.semicolon.exceptions.UsernameAlreadyExistException;
import com.africa.semicolon.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
        assertEquals(1, userService.countUsers());
    }

    @Test
    public void testThatUserServiceCannotSaveUserWithTheSameUsername(){
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username");
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstName("firstName");
        registerUserRequest2.setLastName("lastName");
        assertThrows(UsernameAlreadyExistException.class, ()->userService.register(registerUserRequest2));
    }

    @Test
    public void testSave3UserAndUserServiceCountIs3(){
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username2");
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstName("firstName");
        registerUserRequest2.setLastName("lastName");
        userService.register(registerUserRequest2);

        RegisterUserRequest registerUserRequest3 = new RegisterUserRequest();
        registerUserRequest3.setUsername("username3");
        registerUserRequest3.setPassword("password");
        registerUserRequest3.setFirstName("firstName");
        registerUserRequest3.setLastName("lastName");
        userService.register(registerUserRequest3);

        assertEquals(3, userService.countUsers());
    }

    @Test
    public void testThatUserServiceCanFindUserByUsername(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setFirstName("firstName");
        registerUserRequest.setLastName("lastName");
        userService.register(registerUserRequest);
        User user = userService.findByUsername("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testThatUserServiceThrowExceptionIfUserIsNotFound(){
        assertThrows(UserNotFoundException.class, ()->userService.findByUsername("wrongUsername"));
    }

    @Test
    public void testThatUserServiceCanFindAllUsers(){
        RegisterUserRequest userRequest1 = new RegisterUserRequest();
        userRequest1.setUsername("username1");
        userRequest1.setPassword("password");
        userRequest1.setFirstName("firstName");
        userRequest1.setLastName("lastName");
        userService.register(userRequest1);

        RegisterUserRequest userRequest2 = new RegisterUserRequest();
        userRequest2.setUsername("username2");
        userRequest2.setPassword("password");
        userRequest2.setFirstName("firstName");
        userRequest2.setLastName("lastName");
        userService.register(userRequest2);

        RegisterUserRequest userRequest3 = new RegisterUserRequest();
        userRequest3.setUsername("username3");
        userRequest3.setPassword("password");
        userRequest3.setFirstName("firstName");
        userRequest3.setLastName("lastName");
        userService.register(userRequest3);

        User user1 = userService.findByUsername("username1");
        User user2 = userService.findByUsername("username2");
        User user3 = userService.findByUsername("username3");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        assertEquals(users, userService.findAllUsers());
    }

    @Test
    public void testThatUserServiceCanDeleteUser(){
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);
        assertEquals(1, userService.countUsers());
        userService.deleteUser("username1");
        assertEquals(0, userService.countUsers());
    }

    @Test
    public void testThatUserServiceThrowExceptionIfUserIsNotFoundToDelete(){
        assertThrows(UserNotFoundException.class, ()->userService.deleteUser("wrongUsername"));
    }

    @Test
    public void testThatUserServiceCanUpdateUser(){
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);
        User user1 = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("newFirstName");
        updateUserRequest.setLastName("newLastName");
        updateUserRequest.setUsername("newUsername");
        updateUserRequest.setPassword("newPassword");
        userService.updateUser("username1", "password", updateUserRequest);
        assertThrows(UserNotFoundException.class, ()->userService.findByUsername("username1"));
        User user = userService.findByUsername("newUsername");
        assertEquals("newFirstName", user.getFirstName());
        assertEquals("newLastName", user.getLastName());
    }

    @Test
    public void testThatUserServiceCannotUpdateUserWithWrongUsername(){
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);
        User user1 = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("newFirstName");
        updateUserRequest.setLastName("newLastName");
        updateUserRequest.setUsername("newUsername");
        updateUserRequest.setPassword("newPassword");
        assertThrows(UserNotFoundException.class, ()->userService.updateUser("WrongUsername", "password", updateUserRequest));
    }

    @Test
    public void testThatUserServiceCannotUpdateUserWithWrongPassword(){
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);
        User user1 = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("newFirstName");
        updateUserRequest.setLastName("newLastName");
        updateUserRequest.setUsername("newUsername");
        updateUserRequest.setPassword("newPassword");
        assertThrows(InvalidPasswordException.class, ()->userService.updateUser("username1", "wrongPassword", updateUserRequest));
    }

    @Test
    public void testThatUserCanLoginIfTheyAreRegisteredUser(){
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);

        User user1 = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertEquals("username1", loginResponse.getUsername());
        assertEquals("firstName", loginResponse.getFirstName());
        assertEquals("lastName", loginResponse.getLastName());
    }

}