package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.Constants;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, Model model) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        //System.out.println("multipartFile: " + multipartFile + " fileName: " + fileName);
        Integer userId = userService.getCurrentUser(authentication);
        String message = null;
        if (fileName == null || fileName.isEmpty()) {
            message = Constants.EMPTY_FILE_ERROR;
        } else if(fileService.isFileExists(userId, fileName)) {
            message = Constants.FILE_EXISTS_ERROR;
        } else {
            File file = new File();
            file.setFileName(fileName);
            file.setFileData(multipartFile.getBytes());
            file.setUserId(userId);
            file.setFileSize(String.valueOf(multipartFile.getSize()));
            file.setContentType(multipartFile.getContentType());
            int rowsAdded = fileService.addFile(file);
            if (rowsAdded <= 0) message = Constants.ADD_FILE_ERROR;
        }
        model.addAttribute("fileList", fileService.getFiles(userId));
        model.addAttribute("success", message == null);
        model.addAttribute("message", message);
        return "result";
    }

    @GetMapping("/view")
    public ResponseEntity<Resource> viewFile(@RequestParam(name = "fileId") Integer fileId, Authentication authentication, Model model) {
        Integer userId = userService.getCurrentUser(authentication);
        File file = fileService.getFileById(userId, fileId);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Resource fileResource = new ByteArrayResource(file.getFileData());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFileName() + "\"").body(fileResource);
        }
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam(name = "fileId") Integer fileId, Authentication authentication, Model model) {
        Integer currentUserId = userService.getCurrentUser(authentication);
        String errorMessage = null;
        if (fileService.getFileById(currentUserId, fileId) == null) {
            errorMessage = Constants.DELETE_FILE_ERROR;
        } else {
            int rowsDeleted = fileService.deleteFile(currentUserId, fileId);
            if (rowsDeleted < 0) {
                errorMessage = Constants.DELETE_FILE_ERROR;
            }
        }
        fileService.deleteFile(currentUserId, fileId);
        model.addAttribute("fileList", fileService.getFiles(currentUserId));
        model.addAttribute("success", errorMessage == null);
        model.addAttribute("message", errorMessage);
        return "result";
    }
}
