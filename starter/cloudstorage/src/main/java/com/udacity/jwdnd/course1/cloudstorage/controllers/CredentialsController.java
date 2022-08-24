package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/credentials")
@Controller
public class CredentialsController {

    private final CredentialsService credentialsService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsController(CredentialsService credentialsService, UserService userService, EncryptionService encryptionService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getCredentials(Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        model.addAttribute("credentialList", credentialsService.getCredentialsListForUser(currentUserId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @PostMapping()
    public String addOrUpdateCredential(Credential credential, Authentication authentication, Model model) {
        System.out.println("credential: " + credential);
        Integer currentUserId = userService.getCurrentUser(authentication);
        credential.setUserId(currentUserId);

        Integer credentialId = credential.getCredentialId();
        String errorMessage = null;

        int rowsAdded;
        if (credentialId == null) {
            rowsAdded = credentialsService.addCredential(credential);
        } else {
            rowsAdded = credentialsService.updateCredential(credential);
        }
        if (rowsAdded < 0) {
            errorMessage = "Error: Unable to add credential";
        }

        model.addAttribute("success", errorMessage == null);
        model.addAttribute("message", errorMessage);
        model.addAttribute("credentialList", credentialsService.getCredentialsListForUser(currentUserId));
        model.addAttribute("encryptionService", encryptionService);

        System.out.println("credentials: " + credentialsService.getCredentialsListForUser(currentUserId));
        return "result";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam(name = "credentialId") Integer credentialId, Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        credentialsService.deleteCredential(currentUserId, credentialId);
        model.addAttribute("credentialList", credentialsService.getCredentialsListForUser(currentUserId));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("success", true);
        return "result";
    }
}
