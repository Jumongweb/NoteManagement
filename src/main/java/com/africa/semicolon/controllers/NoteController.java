package com.africa.semicolon.controllers;

import com.africa.semicolon.dtos.request.AddNoteRequest;
import com.africa.semicolon.dtos.response.AddNoteResponse;
import com.africa.semicolon.dtos.response.ApiResponse;
import com.africa.semicolon.exceptions.NoteManagementException;
import com.africa.semicolon.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @PostMapping("/addNote")
    public ResponseEntity<?> addNote(@RequestBody AddNoteRequest addNoteRequest){
        try {
            AddNoteResponse response = noteService.addNote(addNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (NoteManagementException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
