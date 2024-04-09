package com.africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("Users")
public class User {
    private String id;
    private String username;
    private String password;
    private List<Note> notes;
}
