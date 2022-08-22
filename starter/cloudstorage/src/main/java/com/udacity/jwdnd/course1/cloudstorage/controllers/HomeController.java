package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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
    private final FileService fileService;

    public HomeController(NotesService notesService, UserService userService, FileService fileService) {
        this.notesService = notesService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        List<Note> noteList = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        if (currentUserId != null) {
            noteList = notesService.getNotesForUser(currentUserId);
            fileList = fileService.getFiles(currentUserId);
        }
        model.addAttribute("noteList", noteList);
        model.addAttribute("fileList", fileList);
        return "home";
    }
}
