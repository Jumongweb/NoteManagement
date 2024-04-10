package com.africa.semicolon.dtos.request;

import lombok.Data;

@Data
public class AddNoteRequest {
    private String title;
    private String content;
    private String username;
    private String dateCreated;
}
