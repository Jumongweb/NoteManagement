package com.africa.semicolon.dtos.request;

import lombok.Data;

@Data
public class UpdateNoteRequest {
    private String title;
    private String content;
    private String username;

}
