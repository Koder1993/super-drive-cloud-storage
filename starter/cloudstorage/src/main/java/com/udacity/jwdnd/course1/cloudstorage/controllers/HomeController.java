package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;

    public HomeController(NotesService notesService, UserService userService, FileService fileService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.notesService = notesService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        List<Note> noteList = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        List<Credential> credentialList = new ArrayList<>();
        if (currentUserId != null) {
            noteList = notesService.getNotesForUser(currentUserId);
            fileList = fileService.getFiles(currentUserId);
            credentialList = credentialsService.getCredentialsListForUser(currentUserId);
        }
        System.out.println("credentialList: " + credentialList);
        model.addAttribute("noteList", noteList);
        model.addAttribute("fileList", fileList);
        model.addAttribute("credentialList", credentialList);
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
