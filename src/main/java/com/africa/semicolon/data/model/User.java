package com.africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("NoteUsers")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isLoggedIn;
    private List<Note> notes = new ArrayList<>();
    private List<Note> sharedNotes = new ArrayList<>();
    private List<Note> receivedNotes = new ArrayList<>();
}
