package com.africa.semicolon.services;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import com.africa.semicolon.data.repositories.NoteRepository;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.*;
import com.africa.semicolon.dtos.response.*;
import com.africa.semicolon.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.africa.semicolon.utils.Mapper.*;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;
    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.getUsername();
        validate(username);
        User user = mapRegisterUser(registerUserRequest);
        User savedUser = userRepository.save(user);
        return mapRegisterUserResponse(savedUser);
    }

    public void validate(String username){
        for (User user : userRepository.findAll()){
            if (user.getUsername().equals(username)){
                throw new UsernameAlreadyExistException("Username already exist");
            }
        }
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException(String.format("%s does not exist", username));
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public DeleteResponse deleteUser(String username) {
        User user = findByUsername(username);
        userRepository.delete(user);
        return mapDeleteUser(user);
    }

    @Override
    public UpdateUserResponse updateUser(String username, String password, UpdateUserRequest update) {
        User user = findByUsername(username);
        if (!(user.getPassword().equals(password))) { throw new InvalidPasswordException("Invalid password");}
        user.setUsername(update.getUsername());
        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setUsername(update.getUsername());
        user.setPassword(update.getPassword());
        //User updateUser = mapUpdateUser(update);
        User savedUser = userRepository.save(user);

        UpdateUserResponse updateResponse = new UpdateUserResponse();
        updateResponse.setMessage("Updated successfully");
        return updateResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        validate(username, password);
        User user = findByUsername(username);
        user.setLoggedIn(true);
        User savedUser = userRepository.save(user);
        return mapLoginResponse(savedUser);
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        String username = logoutRequest.getUsername();
        User user = findByUsername(username);
        if (user.getUsername().equals(username)) user.setLoggedIn(false);
        User savedUser = userRepository.save(user);
        return mapLogoutResponse(savedUser);
    }

    @Override
    public AddNoteResponse addNote(AddNoteRequest addNoteRequest) {
        Note note = mapAddNote(addNoteRequest);
        validateNote(note.getTitle());
        User user = findByUsername(note.getUsername());
        validateLogin(user.getUsername());
        noteRepository.save(note);
        List<Note> userNote = user.getNotes();
        userNote.add(note);
        user.setNotes(userNote);
        userRepository.save(user);
        return mapAddNoteResponse(note);

    }

    @Override
    public Note findNoteBy(String username1, String title) {
        for (Note note : findAllNotesBelongingToUser(username1)){
            if (note.getTitle().equals(title)){
                return note;
            }
        }
        throw new NoteNotFoundException("Note does not exist");
    }

    @Override
    public List<Note> findAllNotesBelongingToUser(String username) {
        return noteRepository.findNoteByUsername(username);
    }

    private void validateNote(String title) {
        for (Note note : noteRepository.findAll()){
            if (note.getTitle().equals(title)) throw new NoteExistException("Note already exist with the same title");
        }
    }

    private void validateLogin(String username) {
        User user = userRepository.findByUsername(username);
        if (!(user.isLoggedIn())) throw new LoginException("You need to be logged in to use this service");
    }

    public void validate(String username, String password){
        User user = findByUsername(username);
        if (!(user.getPassword().equals(password))) { throw new InvalidPasswordException("Invalid password");}
    }
}
