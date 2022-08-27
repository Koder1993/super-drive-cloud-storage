package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CredentialPage {

    WebDriver driver;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "save-credential-button")
    private WebElement credentialSaveButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlTextInDialog;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameTextInDialog;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordTextInDialog;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "credential-edit-button")
    private WebElement credentialEditButton;

    @FindBy(id = "credential-delete-button")
    private WebElement credentialDeleteButton;

    public CredentialPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void createCredential(CredentialTestInfo credentialTest) {
        credentialsTab.click();
        addCredentialButton.click();
        credentialUrlTextInDialog.sendKeys(credentialTest.getUrl());
        credentialUsernameTextInDialog.sendKeys(credentialTest.getUsername());
        credentialPasswordTextInDialog.sendKeys(credentialTest.getPassword());
        credentialSaveButton.click();
    }

    public void editCredential(CredentialTestInfo credentialTest) {
        credentialUrlTextInDialog.clear();
        credentialUrlTextInDialog.sendKeys(credentialTest.getUrl());
        credentialUsernameTextInDialog.clear();
        credentialUsernameTextInDialog.sendKeys(credentialTest.getUsername());
        credentialPasswordTextInDialog.clear();
        credentialPasswordTextInDialog.sendKeys(credentialTest.getPassword());
        credentialSaveButton.click();
    }

    public void navigateToCredentialsTab() {
        credentialsTab.click();
    }

    public List<CredentialTestInfo> getVisibleCredentials() {
        List<CredentialTestInfo> credentialTestInfoList = new ArrayList<>();
        List<WebElement> urlList = credentialTable.findElements(By.id("credential-url-text"));
        List<WebElement> usernameList = credentialTable.findElements(By.id("credential-username-text"));
        List<WebElement> passwordList = credentialTable.findElements(By.id("credential-password-text"));
        for (int i = 0; i < urlList.size(); i++) {
            credentialTestInfoList.add(new CredentialTestInfo(urlList.get(i).getText(), usernameList.get(i).getText(), passwordList.get(i).getText()));
        }
        return credentialTestInfoList;
    }

    public void selectEditButtonForElement(int index) {
//        credentialEditButton.click();
        List<WebElement> editButtons = credentialTable.findElements(By.id("credential-edit-button"));
        editButtons.get(index).click();
    }

    public String getPasswordInDialog() {
        return credentialPasswordTextInDialog.getAttribute("value");
    }

    public void deleteCredential() throws InterruptedException {
        credentialDeleteButton.click();
        Thread.sleep(2000L);
    }
}
