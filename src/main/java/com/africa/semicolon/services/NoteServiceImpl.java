package com.africa.semicolon.services;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import com.africa.semicolon.data.repositories.NoteRepository;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.AddNoteRequest;
import com.africa.semicolon.dtos.request.DeleteNoteRequest;
import com.africa.semicolon.dtos.request.UpdateNoteRequest;
import com.africa.semicolon.dtos.response.AddNoteResponse;
import com.africa.semicolon.dtos.response.DeleteResponse;
import com.africa.semicolon.dtos.response.UpdateNoteResponse;
import com.africa.semicolon.exceptions.LoginException;
import com.africa.semicolon.exceptions.NoteExistException;
import com.africa.semicolon.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.africa.semicolon.utils.Mapper.*;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;
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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Note> findAllNotesBelongingToUser(String username) {
        validateLogin(username);
        List<Note> notes = new ArrayList<>();
        for (Note note : noteRepository.findAll()){
            if (note.getUsername().equals(username)){
                notes.add(note);
            }
        }
        return notes;
    }

    @Override
    public Long count() {
        return noteRepository.count();
    }

    @Override
    public Note findNoteBy(String username, String title) {
        for (Note note : findAllNotesBelongingToUser(username)){
            if (note.getTitle().equals(title)){
                return note;
            }
        }
        throw new NoteNotFoundException("Note does not exist");
    }

    @Override
    public DeleteResponse deleteNoteBy(DeleteNoteRequest deleteNoteRequest) {
        User user = findByUsername(deleteNoteRequest.getUsername());
        validateLogin(user.getUsername());
        Note note = findNoteBy(deleteNoteRequest.getUsername(), deleteNoteRequest.getTitle());
        noteRepository.delete(note);
        List<Note> notes = user.getNotes();
        notes.remove(note);
        user.setNotes(notes);
        userRepository.save(user);
        return mapDeleteNoteResponse(note);
    }

    @Override
    public UpdateNoteResponse updateNoteBy(UpdateNoteRequest updateNoteRequest) {
        validateLogin(updateNoteRequest.getUsername());
        User user = findByUsername(updateNoteRequest.getUsername());
        Note note = findNoteBy(updateNoteRequest.getUsername(), updateNoteRequest.getTitle());
        note.setTitle(updateNoteRequest.getTitle());
        note.setContent(updateNoteRequest.getContent());
        noteRepository.save(note);
        return mapUpdateNoteResponse(note);
    }


    private void validateLogin(String username) {
        User user = userRepository.findByUsername(username);
        if (!(user.isLoggedIn())) throw new LoginException("You need to be logged in to use this service");
    }

    private void validateNote(String title) {
        for (Note note : noteRepository.findAll()){
            if (note.getTitle().equals(title)) throw new NoteExistException("Note already exist with the same title");
        }
    }
}