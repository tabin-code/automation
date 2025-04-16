package org.tabin.auth;
import io.qameta.allure.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import loginPage.loginToTabin;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class AuthApiTest {

    private String idToken; // Holds the generated ID token
    private String accessToken;
    private loginToTabin loginToTabin; // Assume this is the class containing methods for logging in
    private String email = "tabin.code@gmail.com";
    private String password = "J1a2t3t4!";

    @BeforeClass
    @Step("Setup access token using predefined credentials")
    public void setupIDToken() {
        // Perform one-time setup before any tests run
        loginToTabin = new loginToTabin(); // Initialize login helper or dependency

        Allure.step("Generate ID token using email and password");
        // Assign to the class-level variable
        this.idToken = loginToTabin.generateIDToken(email, password); // Default credentials

        Allure.step("Verify that the ID token has been generated");
        // Use idToken (no need for 'this' in assertions unless disambiguating)
        assertThat("Setup Access token must not be null", idToken, notNullValue());

    }

    //Happy Path
    @Test(description = "Verify Google Authentication Endpoint Functionality")
    @Story("Verify Google Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test case validates that the Google Authentication API accepts a valid ID token and returns appropriate user information.")
    @Epic("User Authentication API Testing")
    @Feature("Google Authentication")
    public void testAuthGoogleEndpoint() {
        Allure.step("Setup Rest-Assured base URI");
        RestAssured.baseURI = "https://tabin.io/api";

        Allure.step("Send POST request with valid ID token to /auth/google endpoint");
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"idToken\": \"" + this.idToken + "\" }")
                .when()
                .post("/auth/google")
                .then()
                .statusCode(200) // Expect 200 OK
                .extract()
                .response();

        Allure.step("Attach API response to Allure report");
        Allure.addAttachment("API Response", response.asString());
        System.out.println("Response Body: " + response.asString());

        // Extract the access token from the response and store it
        Allure.step("Extract access token from the response");
        String accessToken = response.jsonPath().getString("token");
        assertThat("Access token should not be null or empty", accessToken, not(emptyString()));

        // Store the access token for further use (class-level variable)
        this.accessToken = accessToken; // Assuming 'accessToken' is declared as a class-level variable

        Allure.step("Validate response contains token and user details");
        response.then().assertThat()
                .body("token", not(emptyString())) // Check if token exists and is not empty
                .body("user._id", not(emptyString()))
                .body("user.first_name", equalTo("Tabin"))
                .body("user.last_name", equalTo("io"))
                .body("user.email", equalTo("tabin.code@gmail.com"))
                .body("user.google_id", not(emptyString()))
                .body("user.image_url", not(emptyString()));

        assertThat("Access token should not be null or empty", accessToken, not(emptyString()));

        System.out.println("Access Token: " + accessToken);
    }

    @Test(description = "Verify /auth/google endpoint returns 400 for null ID token")
    @Story("Verify Google Authentication")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test validates that the API returns 400 Bad Request when the ID token is null and includes the error message 'Invalid Google token'.")
    @Epic("Authentication API Testing")
    @Feature("Google Authentication")
    public void testAuthGoogleEndpointWithNullIdToken() {
        Allure.step("Setup Rest-Assured base URI");
        RestAssured.baseURI = "https://tabin.io/api";

        Allure.step("Send POST request with null ID token to /auth/google endpoint");
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"idToken\": null }") // Null token
                .when()
                .post("/auth/google")
                .then()
                .statusCode(400) // Expect 400 Bad Request
                .extract()
                .response();

        Allure.step("Attach API response to Allure report");
        Allure.addAttachment("API Response", response.asString());
        System.out.println("Response Body: " + response.asString());

        // Assert status code is correct (400)
        Allure.step("Validate status code is 400");
        Assert.assertEquals(response.statusCode(), 400, "Expected status code 400 but got " + response.statusCode());

        // Assert the response contains the correct error message
        Allure.step("Validate the 'message' field in the response");
        String message = response.jsonPath().getString("message");
        Assert.assertNotNull(message, "Error message missing in response");
        Assert.assertEquals(message, "Invalid Google token", "Unexpected error message in response");
    }

    @Test(description = "Verify /auth/google endpoint with invalid ID token")
    @Story("Verify Google Authentication")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test validates that the API returns an error when the ID token is invalid.")
    @Epic("Authentication API Testing")
    @Feature("Google Authentication")
    public void testAuthGoogleEndpointWithInvalidIdToken() {
        Allure.step("Setup Rest-Assured base URI");
        RestAssured.baseURI = "https://tabin.io/api";

        Allure.step("Send POST request with an invalid ID token to /auth/google endpoint");
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"idToken\": \"invalid_token\" }") // Invalid token
                .when()
                .post("/auth/google")
                .then()
                .statusCode(401) // Expect 401 Unauthorized
                .extract()
                .response();

        Allure.step("Attach API response to Allure report");
        Allure.addAttachment("API Response", response.asString());
        System.out.println("Response Body: " + response.asString());

        Allure.step("Validate error response");
        response.then().assertThat()
                .body("error", equalTo("Unauthorized")); // Example error message
    }
}




