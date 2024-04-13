package com.africa.semicolon.services;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import com.africa.semicolon.data.repositories.NoteRepository;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.*;
import com.africa.semicolon.dtos.response.LoginResponse;
import com.africa.semicolon.exceptions.*;
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

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        noteRepository.deleteAll();
    }

    @Test
    public void testThatUserServiceCanSaveUser() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setFirstName("firstName");
        registerUserRequest.setLastName("lastName");
        userService.register(registerUserRequest);
        assertEquals(1, userService.countUsers());
    }

    @Test
    public void testThatUserServiceCannotSaveUserWithTheSameUsername() {
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
        assertThrows(UsernameAlreadyExistException.class, ()-> userService.register(registerUserRequest2));
    }

    @Test
    public void testSave3UserAndUserServiceCountIs3() {
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
    public void testThatUserServiceCanFindUserByUsername() {
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
    public void testThatUserServiceThrowExceptionIfUserIsNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("wrongUsername"));
    }

    @Test
    public void testThatUserServiceCanFindAllUsers() {
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
    public void testThatUserServiceCanDeleteUser() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);
        assertEquals(1, userService.countUsers());
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername("username1");
        userService.deleteUser(deleteUserRequest);
        assertEquals(0, userService.countUsers());
    }

    @Test
    public void testThatUserServiceThrowExceptionIfUserIsNotFoundToDelete() {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername("unregisteredUsername");
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(deleteUserRequest));
    }

    @Test
    public void testThatUserServiceCanUpdateUser() {
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
        updateUserRequest.setUsername("username1");
        updateUserRequest.setPassword("password");
        userService.updateUser(updateUserRequest);
        User user = userService.findByUsername("username1");
        assertEquals("newFirstName", user.getFirstName());
        assertEquals("newLastName", user.getLastName());
    }

    @Test
    public void testThatUserServiceCannotUpdateUserWithWrongUsername() {
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
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(updateUserRequest));
    }

    @Test
    public void testThatUserServiceCannotUpdateUserWithWrongPassword() {
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
        updateUserRequest.setUsername("username1");
        updateUserRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, () -> userService.updateUser(updateUserRequest));
    }

    @Test
    public void testThatUserCanLoginIfTheyAreRegisteredUser() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
        assertEquals(loginResponse.getMessage(), "Login successful");
    }

    @Test
    public void testThatUserCannotLoginIfTheyAreNotRegisteredUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("unregisteredUser");
        loginRequest.setPassword("password");
        assertThrows(UserNotFoundException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void testThatUserCannotLoginWithWrongPasswordIfTheyAreRegisteredUser() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, () -> userService.login(loginRequest));
        user = userService.findByUsername("username1");
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testThatUserCanLogout() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username1");
        userService.logout(logoutRequest);
        user = userService.findByUsername("username1");
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testThatUserCannotLogoutWithWrongUsername() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("wrongUsername");
        assertThrows(UserNotFoundException.class, () -> userService.logout(logoutRequest));
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
    }

    @Test
    public void testLoginInThreeDifferentUsers() {
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

        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");

        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());
    }

    @Test
    public void testLoginInThreeDifferentUsersLogoutTheMiddle() {
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

        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");

        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username2");
        userService.logout(logoutRequest);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertFalse(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());
    }

    @Test
    public void testLoginInThreeDifferentUsersLogoutTheMiddleAndLoginAgain() {
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

        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");

        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username2");
        userService.logout(logoutRequest);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertFalse(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());

        userService.login(loginRequest2);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());
    }

    @Test
    public void testThatUserServiceCanAddNoteToUserList() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUsername("username1");
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content of the note");
        userService.addNote(addNoteRequest);
        assertEquals(1, noteRepository.count());
        assertEquals(1, userRepository.findByUsername("username1").getNotes().size());
    }

    @Test
    public void testThatUserServiceCanAddNoteWithSameTitle() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUsername("username1");
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content of the note");
        userService.addNote(addNoteRequest);
        assertEquals(1, noteRepository.count());
        assertEquals(1, userRepository.findByUsername("username1").getNotes().size());
        assertThrows(NoteExistException.class, () -> userService.addNote(addNoteRequest));
    }

    @Test
    public void testThatNoteServiceCannotAddNoteWithSameTitle() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUsername("username1");
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content of the note");
        userService.addNote(addNoteRequest);
        assertEquals(1, noteRepository.count());
        assertEquals(1, userRepository.findByUsername("username1").getNotes().size());
        assertThrows(NoteExistException.class, () -> noteService.addNote(addNoteRequest));
    }

    @Test
    public void testThatUserServiceCannotAddNoteToUserListIfTheyAreNotLoggedIn() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUsername("username1");
        addNoteRequest.setTitle("title");
        addNoteRequest.setContent("Content of the note");
        assertThrows(LoginException.class, () -> userService.addNote(addNoteRequest));
    }

    @Test
    public void test3DifferentUsersCreateOneNoteEach() {
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

        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");
        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);

        AddNoteRequest addNoteRequest1 = new AddNoteRequest();
        addNoteRequest1.setUsername("username1");
        addNoteRequest1.setTitle("title");
        addNoteRequest1.setContent("Content of the note");
        userService.addNote(addNoteRequest1);

        AddNoteRequest addNoteRequest2 = new AddNoteRequest();
        addNoteRequest2.setUsername("username2");
        addNoteRequest2.setTitle("title2");
        addNoteRequest2.setContent("Content of the note");
        userService.addNote(addNoteRequest2);

        AddNoteRequest addNoteRequest3 = new AddNoteRequest();
        addNoteRequest3.setUsername("username3");
        addNoteRequest3.setTitle("title3");
        addNoteRequest3.setContent("Content of the note");
        userService.addNote(addNoteRequest3);

        assertEquals(3, noteRepository.count());
        assertEquals(1, userRepository.findByUsername("username1").getNotes().size());
        assertEquals(1, userRepository.findByUsername("username2").getNotes().size());
        assertEquals(1, userRepository.findByUsername("username3").getNotes().size());
    }

    @Test
    public void testThatUserServiceCanFindAllNoteBelongingToOneUser() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest1 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        userService.login(loginRequest1);
        AddNoteRequest addNoteRequest1 = new AddNoteRequest();
        addNoteRequest1.setUsername("username1");
        addNoteRequest1.setTitle("title1");
        addNoteRequest1.setContent("Content of the note");
        userService.addNote(addNoteRequest1);

        AddNoteRequest addNoteRequest2 = new AddNoteRequest();
        addNoteRequest2.setUsername("username1");
        addNoteRequest2.setTitle("title2");
        addNoteRequest2.setContent("Content of the note");
        userService.addNote(addNoteRequest2);

        AddNoteRequest addNoteRequest3 = new AddNoteRequest();
        addNoteRequest3.setUsername("username1");
        addNoteRequest3.setTitle("title3");
        addNoteRequest3.setContent("Content of the note");
        userService.addNote(addNoteRequest3);

        List<Note> sampleNote = userService.findAllNotesBelongingToUser("username1");
        assertEquals(3, noteRepository.count());
        assertEquals(sampleNote.size(), userService.findByUsername("username1").getNotes().size());
    }

    @Test
    public void testThatNoteServiceCanFindAllNoteBelongingToOneUser() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstName("firstName");
        registerUserRequest1.setLastName("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest1 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        userService.login(loginRequest1);
        AddNoteRequest addNoteRequest1 = new AddNoteRequest();
        addNoteRequest1.setUsername("username1");
        addNoteRequest1.setTitle("title1");
        addNoteRequest1.setContent("Content of the note");
        userService.addNote(addNoteRequest1);

        AddNoteRequest addNoteRequest2 = new AddNoteRequest();
        addNoteRequest2.setUsername("username1");
        addNoteRequest2.setTitle("title2");
        addNoteRequest2.setContent("Content of the note");
        userService.addNote(addNoteRequest2);

        AddNoteRequest addNoteRequest3 = new AddNoteRequest();
        addNoteRequest3.setUsername("username1");
        addNoteRequest3.setTitle("title3");
        addNoteRequest3.setContent("Content of the note");
        userService.addNote(addNoteRequest3);

        List<Note> sampleNote = userService.findAllNotesBelongingToUser("username1");
        assertEquals(3, noteRepository.count());
        assertEquals(sampleNote.size(), userService.findByUsername("username1").getNotes().size());
    }


}