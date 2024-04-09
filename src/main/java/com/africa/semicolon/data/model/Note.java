package com.africa.semicolon.data.model;

import java.time.LocalDateTime;

public class Note {
    private String id;
    private String title;
    private String content;
    private LocalDateTime dateCreated = LocalDateTime.now();
}
