package com.africa.semicolon.dtos.request;

import com.africa.semicolon.data.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddNoteRequest {
    private String title;
    private String content;
    private String username;
    //private String dateCreated;
}
