package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/home")
@Controller
public class HomeController {

    private final NotesService notesService;
    private final UserService userService;

    public HomeController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        List<Note> noteList = new ArrayList<>();
        if (currentUserId != null) {
            noteList = notesService.getNotesForUser(currentUserId);
        }
        model.addAttribute("noteList", noteList);
        return "home";
    }
}
