package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/notes")
@Controller
public class NotesController {

    private final NotesService notesService;
    private final UserService userService;

    public NotesController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping()
    public String getNotesList(Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        List<Note> noteList = new ArrayList<>();
        if (currentUserId != null) {
            noteList = notesService.getNotesForUser(currentUserId);
        }
        model.addAttribute("notesList", noteList);
        return "home";
    }

    @PostMapping()
    public String addOrUpdateNote(Note newNote, Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        newNote.setUserId(currentUserId);
        String errorMessage = null;
        Integer currentNoteId = newNote.getNoteId();
        int rowsAdded;
        if (currentNoteId == null) {
            rowsAdded = notesService.addNote(newNote);
        } else {
            rowsAdded = notesService.updateNote(newNote);
        }
        if (rowsAdded < 0) {
            errorMessage = "Error: Unable to add note";
        }
        model.addAttribute("success", errorMessage == null);
        model.addAttribute("message", errorMessage);
        model.addAttribute("noteList", notesService.getNotesForUser(currentUserId));
        System.out.println("notes: " + notesService.getNotesForUser(currentUserId));
        return "result";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam(name = "noteId") Integer noteId, Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        notesService.deleteNote(currentUserId, noteId);
        model.addAttribute("noteList", notesService.getNotesForUser(currentUserId));
        model.addAttribute("success", true);
        return "result";
    }
}
