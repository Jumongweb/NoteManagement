package com.africa.semicolon.dtos.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateNoteRequest {
    private String title;
    private String content;
    private String username;

}
