package com.africa.semicolon.dtos.request;

import lombok.Data;

@Data
public class ShareNoteRequest {
    private String sender;
    private String receiver;
    private String title;
}
