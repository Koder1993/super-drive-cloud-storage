package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private final NotesMapper notesMapper;

    public NotesService(NotesMapper noteMapper) {
        this.notesMapper = noteMapper;
    }

    public int addNote(Note note) {
        return notesMapper.insertNote(note);
    }

    public void deleteNote(Integer currentUserId, Integer noteId) {
        notesMapper.deleteNote(currentUserId, noteId);
    }

    public int updateNote(Note note) {
        return notesMapper.updateNote(note);
    }

    public List<Note> getNotesForUser(Integer userId) {
        return notesMapper.getNotesForUser(userId);
    }
}
