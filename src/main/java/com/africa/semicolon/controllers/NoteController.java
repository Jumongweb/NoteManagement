package com.africa.semicolon.controllers;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.dtos.request.AddNoteRequest;
import com.africa.semicolon.dtos.request.DeleteNoteRequest;
import com.africa.semicolon.dtos.request.UpdateNoteRequest;
import com.africa.semicolon.dtos.response.AddNoteResponse;
import com.africa.semicolon.dtos.response.ApiResponse;
import com.africa.semicolon.dtos.response.DeleteNoteResponse;
import com.africa.semicolon.dtos.response.UpdateNoteResponse;
import com.africa.semicolon.exceptions.NoteManagementException;
import com.africa.semicolon.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/findNote/{username}/{title}")
    public ResponseEntity<?> findNote(
            @PathVariable("username") String username,
            @PathVariable("title") String title){
        try {
            return new ResponseEntity<>(new ApiResponse(true, noteService.findNoteBy(username, title)), HttpStatus.OK);
        } catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("deleteNote")
    public ResponseEntity<?> deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest){
        try {
            DeleteNoteResponse response = noteService.deleteNoteBy(deleteNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (NoteManagementException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("updateNote")
    public ResponseEntity<?> updateNote(@RequestBody UpdateNoteRequest updateNoteRequest){
        try{
            UpdateNoteResponse response = noteService.updateNoteBy(updateNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (NoteManagementException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
