package com.africa.semicolon.dtos.request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeleteNoteRequest {
    private String username;
    private String title;
}
