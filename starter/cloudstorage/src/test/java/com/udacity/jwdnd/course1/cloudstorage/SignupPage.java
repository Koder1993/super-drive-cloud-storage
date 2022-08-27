package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSignUp")
    private WebElement submitButton;

    @FindBy(id = "signup-error-msg")
    private WebElement errorMessage;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstName, String lastName, String userName, String password) {
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        inputLastName.click();
        inputLastName.sendKeys(lastName);

        inputUsername.click();
        inputUsername.sendKeys(userName);

        inputPassword.click();
        inputPassword.sendKeys(password);

        submitButton.click();
    }
}