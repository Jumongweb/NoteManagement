package com.africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Notes")
public class Note {
    @Id
    private String id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime dateCreated = LocalDateTime.now();
}
