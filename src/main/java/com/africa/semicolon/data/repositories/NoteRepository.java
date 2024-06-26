package com.africa.semicolon.data.repositories;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findNoteByUsername(String username);

    User findByUsername(String username);
}
