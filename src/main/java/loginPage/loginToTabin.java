package loginPage;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class loginToTabin {

    private static final Logger log = LoggerFactory.getLogger(loginToTabin.class);

    public String generateIDToken(String email, String password) {
        // Set the property to locate the geckodriver executable
        System.setProperty("webdriver.gecko.driver", "/Users/joti/Documents/4_Tabin/Tabin Automation/tabin-automation/geckodriver");

        // Set up Selenium WebDriver options
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-profile", "/Users/joti/Library/Application Support/Firefox/Profiles/r6kuuxfk.Tabin-Code");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new FirefoxDriver(options); // Initialize the WebDriver

        try {
            // Step 1: Open Tabin's login page
            log.info("Navigating to Tabin's login page...");
            driver.get("https://tabin.io/login");

            // Step 2: Wait for and click "Continue with Google" button
            log.info("Waiting for 'Continue with Google' button...");
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Continue with Google')]")
            ));
            loginButton.click();

            // Step 3: Click "Use another account"
            log.info("Waiting for 'Use another account' option...");
            WebElement useAnotherAccountButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'AsY17b')]")
            ));
            useAnotherAccountButton.click();
            log.info("Successfully clicked 'Use another account' option.");

            // Step 4: Enter email
            log.info("Entering email...");
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("identifier")));
            emailField.sendKeys(email);
            WebElement emailNextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("identifierNext")));
            emailNextButton.click();

            // Step 5: Enter password
            log.info("Entering password...");
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='password']")));
            passwordField.sendKeys(password);
            WebElement passwordNextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext")));
            passwordNextButton.click();

            // Step 6: Click "Continue" post-login (if required)
            log.info("Waiting for 'Continue' button...");
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(), 'Continue')]")
            ));
            continueButton.click();

            // Step 7: Wait for the page to load and retrieve `accessToken`
            log.info("Waiting for the dashboard to load...");
            wait.until(ExpectedConditions.urlContains("mytabins")); // Replace "mytabins" with the relevant URL indicator

            log.info("Attempting to retrieve 'accessToken' from sessionStorage...");
            WebDriverWait localStorageWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            String accessToken = (String) localStorageWait.until(driverInstance ->
                    ((JavascriptExecutor) driverInstance).executeScript(
                            "return window.localStorage.getItem('idToken');"
                    )
            );

            log.info("idToken successfully retrieved.");
            return accessToken;

        } catch (Exception e) {
            log.error("An error occurred during the login process: ", e);
            return null;
        } finally {
            // Ensure the browser is closed after the process is complete
            log.info("Closing the browser...");
            driver.quit();
        }
    }
}

