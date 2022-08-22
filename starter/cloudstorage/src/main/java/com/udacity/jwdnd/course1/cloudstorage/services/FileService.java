package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int addFile(File file) {
        return fileMapper.addFile(file);
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getFilesForUser(userId);
    }

    @Nullable
    public File getFileByName(Integer userId, String fileName) {
        return fileMapper.getFileByName(userId, fileName);
    }

    // Though userId is not strictly required for this use-case, it is kept here in-case a future change is done where fileIds are not unique across users
    @Nullable
    public File getFileById(Integer userId, Integer fileId) {
        return fileMapper.getFileById(userId, fileId);
    }

    public void deleteFile(Integer currentUserId, Integer fileId) {
        fileMapper.deleteFile(currentUserId, fileId);
    }

    public boolean isFileExists(Integer userId, String fileName) {
        return getFileByName(userId, fileName) != null;
    }
}
