package com.africa.semicolon.dtos.request;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    private String username;
    private String title;
}
