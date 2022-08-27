package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class NotePage {
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInDialog;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInDialog;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "success-redirect-link")
    private WebElement successRedirectLink;

    @FindBy(id = "note-title-main")
    private WebElement noteTitleMain;

    @FindBy(id = "note-description-main")
    private WebElement noteDescriptionMain;

    @FindBy(id = "note-edit-button")
    private WebElement noteEditButton;

    @FindBy(id = "note-delete-button")
    private WebElement noteDeleteButton;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createNote(String noteTitle, String noteDescription) {
        notesTab.click();
        addNoteButton.click();
        noteTitleInDialog.sendKeys(noteTitle);
        noteDescriptionInDialog.sendKeys(noteDescription);
        saveNoteButton.click();
    }

    public void editNote(String newNoteTitle, String newNoteDescription) {
        notesTab.click();
        noteEditButton.click();
        noteTitleInDialog.clear();
        noteTitleInDialog.sendKeys(newNoteTitle);
        noteDescriptionInDialog.clear();
        noteDescriptionInDialog.sendKeys(newNoteDescription);
        saveNoteButton.click();
    }

    public void deleteNote() {
        notesTab.click();
        noteDeleteButton.click();
    }

    public String getFirstNoteTitleText() {
        return noteTitleMain.getText();
    }

    public String getFirstNoteDescriptionText() {
        return noteDescriptionMain.getText();
    }

    public void navigateToNotesTab() {
        notesTab.click();
    }

    public boolean isNoteTableEmpty() {
        return noteTable.findElements(By.id("note-title-main")).isEmpty();
    }
}
