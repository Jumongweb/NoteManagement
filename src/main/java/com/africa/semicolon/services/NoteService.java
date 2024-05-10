package com.africa.semicolon.services;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import com.africa.semicolon.dtos.request.AddNoteRequest;
import com.africa.semicolon.dtos.request.DeleteNoteRequest;
import com.africa.semicolon.dtos.request.ShareNoteRequest;
import com.africa.semicolon.dtos.request.UpdateNoteRequest;
import com.africa.semicolon.dtos.response.*;
import com.mongodb.internal.bulk.DeleteRequest;

import java.net.ContentHandler;
import java.util.List;

public interface NoteService {
    AddNoteResponse addNote(AddNoteRequest addNoteRequest);

    User findByUsername(String username1);

    List<Note> findAllNotesBelongingToUser(String username1);

    Long count();

    Note findNoteBy(String username, String title);

    DeleteNoteResponse deleteNoteBy(DeleteNoteRequest deleteNoteRequest);

    UpdateNoteResponse updateNoteBy(UpdateNoteRequest updateNoteRequest);

    List<Note> getAllNotes();

    ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest);
}
