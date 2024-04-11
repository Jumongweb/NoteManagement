package com.africa.semicolon.services;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.repositories.NoteRepository;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.*;
import com.africa.semicolon.exceptions.LoginException;
import com.africa.semicolon.exceptions.NoteExistException;
import com.africa.semicolon.exceptions.NoteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoteServiceImplTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        noteRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    public void testThatNoteServiceCanAddNoteToUserList() {
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
        noteService.addNote(addNoteRequest);
        assertEquals(1, noteService.count());
        assertEquals(1, noteService.findByUsername("username1").getNotes().size());
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
        assertEquals(1, noteService.count());
        assertEquals(1, userService.findByUsername("username1").getNotes().size());
        assertThrows(NoteExistException.class, () -> noteService.addNote(addNoteRequest));
    }

    @Test
    public void testThatNoteServiceCannotAddNoteToUserListIfTheyAreNotLoggedIn() {
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
        assertThrows(LoginException.class, () -> noteService.addNote(addNoteRequest));
    }

    @Test
    public void testOneUserCreate3Note_NoteServiceCountIs3() {
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

        assertEquals(3, noteRepository.count());
        assertEquals(3, userRepository.findByUsername("username1").getNotes().size());
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

    @Test
    public void testThatNoteServiceCanFindNote() {
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
        noteService.addNote(addNoteRequest1);
        assertEquals("Content of the note", noteService.findNoteBy("username1", "title1").getContent());
    }

    @Test
    public void testThatUserServiceThrowExceptionIfNoteThatDoesNotExistIsSearchedFor() {
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
        assertThrows(NoteNotFoundException.class,()->userService.findNoteBy("username1", "wrongTitle").getContent());
    }

    @Test
    public void testThatNoteServiceCanDeleteNote(){
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
        noteService.addNote(addNoteRequest1);
        assertEquals(1, noteService.count());
        assertEquals(1, noteService.findByUsername("username1").getNotes().size());
        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("username1");
        deleteNoteRequest.setTitle("title1");
        noteService.deleteNoteBy(deleteNoteRequest);
        assertEquals(0, noteService.count());
        assertEquals(0, noteService.findByUsername("username1").getNotes().size());
    }

    @Test
    public void testThatNoteServiceIfUserDeleteNoteIfTheyAreNotLogin(){
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
        noteService.addNote(addNoteRequest1);
        assertEquals(1, noteService.count());
        assertEquals(1, noteService.findByUsername("username1").getNotes().size());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username1");
        userService.logout(logoutRequest);
        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("username1");
        deleteNoteRequest.setTitle("title1");
        assertThrows(LoginException.class, ()->noteService.deleteNoteBy(deleteNoteRequest));
        assertEquals(1, noteService.count());
        assertEquals(1, noteService.findByUsername("username1").getNotes().size());
    }

    @Test
    public void testThatNoteServiceCanUpdateNote(){
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
        noteService.addNote(addNoteRequest1);
        assertEquals("Content of the note", noteService.findNoteBy("username1", "title1").getContent());
        assertEquals(1, noteService.count());
        assertEquals(1, noteService.findByUsername("username1").getNotes().size());
        UpdateNoteRequest updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setUsername("username1");
        updateNoteRequest.setTitle("title1");
        updateNoteRequest.setContent("Updated Content of the note");
        noteService.updateNoteBy(updateNoteRequest);
        assertEquals("Updated Content of the note", noteService.findNoteBy("username1", "title1").getContent());

        assertEquals(1, noteService.count());
        assertEquals(1, noteService.findByUsername("username1").getNotes().size());
    }

}