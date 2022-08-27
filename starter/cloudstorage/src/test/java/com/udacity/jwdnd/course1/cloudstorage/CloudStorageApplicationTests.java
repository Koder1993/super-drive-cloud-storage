package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new FirefoxDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    // TCs related to signup/login/logout/unauthorized access

    @Test
    public void testUnauthorizedAccess() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }

    @Test
    public void testSignupLoginLogoutFlow() {
        // Sign-up flow
        SignupPage signupPage = new SignupPage(driver);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        signupPage.signup("Firstname", "Lastname", "TestUsername", "1234");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl()); // verify successful redirection

        // Log-in flow
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logIn("TestUsername", "1234");
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl()); // verify successful login

        // Log-out flow
        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();
        Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    // TCs for notes management

    @Test
    public void testCreateNote() {
        doMockSignUp("CreateNoteFirst", "CreateNoteLast", "CreateNote", "1234");
        doLogIn("CreateNote", "1234");

        NotePage notePage = new NotePage(driver);
        String noteTitle = "CreateNote";
        String noteDescription = "Hello World!!";
        notePage.createNote(noteTitle, noteDescription);

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
        driver.findElement(By.id("success-redirect-link")).click();

        Assertions.assertEquals("Home", driver.getTitle());
        notePage.navigateToNotesTab();
        Assertions.assertEquals(noteTitle, notePage.getFirstNoteTitleText());
        Assertions.assertEquals(noteDescription, notePage.getFirstNoteDescriptionText());
    }

    @Test
    public void testEditNote() {
        // create new note first
        doMockSignUp("EditNoteFirst", "EditNoteLast", "EditNote", "1234");
        doLogIn("EditNote", "1234");

        NotePage notePage = new NotePage(driver);
        String noteTitle = "CreateNote";
        String noteDescription = "Note Description 1";
        notePage.createNote(noteTitle, noteDescription);
        driver.findElement(By.id("success-redirect-link")).click();

        // edit note
        String newNoteTitle = "EditNote";
        String newNoteDescription = "Note Description 2";
        notePage.editNote(newNoteTitle, newNoteDescription);

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
        driver.findElement(By.id("success-redirect-link")).click();

        Assertions.assertEquals("Home", driver.getTitle());
        notePage.navigateToNotesTab();
        Assertions.assertEquals(newNoteTitle, notePage.getFirstNoteTitleText());
        Assertions.assertEquals(newNoteDescription, notePage.getFirstNoteDescriptionText());
    }

    @Test
    public void testDeleteNote() {
        // create new note first
        doMockSignUp("DeleteNoteFirst", "DeleteNoteLast", "DeleteNote", "1234");
        doLogIn("DeleteNote", "1234");

        NotePage notePage = new NotePage(driver);
        String noteTitle = "CreateNote";
        String noteDescription = "Note Description 1";
        notePage.createNote(noteTitle, noteDescription);
        driver.findElement(By.id("success-redirect-link")).click();

        // delete note
        notePage.deleteNote();
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
        driver.findElement(By.id("success-redirect-link")).click();

        Assertions.assertEquals("Home", driver.getTitle());
        notePage.navigateToNotesTab();
        Assertions.assertThrows(NoSuchElementException.class, notePage::getFirstNoteTitleText);
    }

    // TCs for credentials management

    private List<CredentialTestInfo> generateUserCredentialList() {
        List<CredentialTestInfo> credentialList = new ArrayList<>();
        credentialList.add(new CredentialTestInfo("http://test1.com", "Test1", "1234"));
        credentialList.add(new CredentialTestInfo("http://test2.com", "Test2", "2345"));
        credentialList.add(new CredentialTestInfo("http://test3.com", "Test3", "3456"));
        return credentialList;
    }

    @Test
    public void testCreateCredentials() {
        doMockSignUp("CreateCredential", "CreateCredential", "CreateCredential", "1234");
        doLogIn("CreateCredential", "1234");

        CredentialPage credentialPage = new CredentialPage(driver);
        List<CredentialTestInfo> credentialList = generateUserCredentialList();

        for (CredentialTestInfo credentialTestInfo : credentialList) {
            credentialPage.createCredential(credentialTestInfo);
            Assertions.assertEquals("Result", driver.getTitle());
            driver.findElement(By.id("success-redirect-link")).click();
        }
        credentialPage.navigateToCredentialsTab();

        List<CredentialTestInfo> visibleCredentials = credentialPage.getVisibleCredentials();
        for (int i = 0; i < credentialList.size(); i++) {
            CredentialTestInfo credential = credentialList.get(i);
            CredentialTestInfo visibleCredential = visibleCredentials.get(i);
            Assertions.assertEquals(credential.getUrl(), visibleCredential.getUrl());
            Assertions.assertEquals(credential.getUsername(), visibleCredential.getUsername());
            Assertions.assertNotEquals(credential.getPassword(), visibleCredential.getPassword()); // verifies that displayed password is encrypted
        }
    }

    @Test
    public void testEditCredentials() throws InterruptedException {
        doMockSignUp("EditCredential", "EditCredential", "EditCredential", "1234");
        doLogIn("EditCredential", "1234");

        CredentialPage credentialPage = new CredentialPage(driver);
        List<CredentialTestInfo> credentialList = generateUserCredentialList();

        for (CredentialTestInfo credentialTestInfo : credentialList) {
            credentialPage.createCredential(credentialTestInfo);
            Assertions.assertEquals("Result", driver.getTitle());
            driver.findElement(By.id("success-redirect-link")).click();
        }
        credentialPage.navigateToCredentialsTab();
        credentialPage.selectEditButtonForElement(0);
        Thread.sleep(2000L);
        Assertions.assertEquals(credentialList.get(0).getPassword(), credentialPage.getPasswordInDialog()); // verify that dialog password is decrypted

        CredentialTestInfo updatedCredential = new CredentialTestInfo("http://test10.com", "Test10", "4567");
        credentialPage.editCredential(updatedCredential);
        Assertions.assertEquals("Result", driver.getTitle());
        driver.findElement(By.id("success-redirect-link")).click();
        credentialPage.navigateToCredentialsTab();

        CredentialTestInfo visibleCredential = credentialPage.getVisibleCredentials().get(0); // get the updated credential
        // verify that updated changes are present
        Assertions.assertEquals(updatedCredential.getUrl(), visibleCredential.getUrl());
        Assertions.assertEquals(updatedCredential.getUsername(), visibleCredential.getUsername());
        Assertions.assertNotEquals(updatedCredential.getPassword(), visibleCredential.getPassword());
    }

    @Test
    public void testDeleteCredentials() throws InterruptedException {
        doMockSignUp("DeleteCredential", "DeleteCredential", "DeleteCredential", "1234");
        doLogIn("DeleteCredential", "1234");

        CredentialPage credentialPage = new CredentialPage(driver);
        List<CredentialTestInfo> credentialList = generateUserCredentialList();

        for (CredentialTestInfo credentialTestInfo : credentialList) {
            credentialPage.createCredential(credentialTestInfo);
            Assertions.assertEquals("Result", driver.getTitle());
            driver.findElement(By.id("success-redirect-link")).click();
        }
        credentialPage.navigateToCredentialsTab();

        for (int i = 0; i < credentialList.size(); i++) {
            credentialPage.deleteCredential();
            driver.findElement(By.id("success-redirect-link")).click();
            credentialPage.navigateToCredentialsTab();
        }
        Assertions.assertTrue(credentialPage.getVisibleCredentials().isEmpty());
    }
}
