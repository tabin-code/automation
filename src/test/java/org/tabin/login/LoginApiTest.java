package org.tabin.login;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.testng.TestInstanceParameter;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import java.util.Properties;

import static helperTools.propertiesLoader.loadProperties;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class LoginApiTest {
}
//    @TestInstanceParameter("Login")
//    private String login;
//
//    @TestInstanceParameter("Password")
//    private String passwordForTests;
//
//    private static final Properties API_CONFIG = loadProperties("api-config.properties");
//    private static final Properties OAUTH_GOOGLE_CONFIG = loadProperties("oauth-config.properties");
//
//    private static final String OAUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
//    private static final String GOOGLE_USER_INFO_API = "https://www.googleapis.com/oauth2/v3/userinfo";
//
//    // Load client ID dynamically from properties
//    private static final String CLIENT_ID = OAUTH_GOOGLE_CONFIG.getProperty("oauth.google.client.id", "your-fallback-client-id.apps.googleusercontent.com");
//    private static final String REDIRECT_URI = "http://localhost:3000/login";
//    private static final String SCOPES = "email profile";
//
//    @Factory(dataProvider = "authenticationData")
//    public LoginApiTest(String login, String password) {
//        this.login = login;
//        this.passwordForTests = password;
//    }
//
//    @DataProvider
//    public static Object[][] authenticationData() {
//        return new Object[][]{
//                {"tabin-code", "J1a2t3t4!"},
//                {"tabin-code@gmail.com", "J1a2t3t4"}
//        };
//    }
//
//    @Test(testName = "Verify Google OAuth endpoint is reachable")
//    @Description("This test checks if the OAuth endpoint is accessible and returns an HTTP 200 status.")
//    public void testGoogleOAuthEndpoint_IsReachable() {
//        Response response = sendOAuthRequest(CLIENT_ID, REDIRECT_URI);
//
//        Allure.step("Validating that the response status code is 200.");
//        assertThat(response.getStatusCode(), is(200));
//    }
//
//    @Test(testName = "Test OAuth endpoint with login and password")
//    @Description("This test verifies that login and password can be passed with the OAuth endpoint.")
//    public void testOAuthEndpointWithLogin() {
//        Response response = given()
//                .queryParam("client_id", CLIENT_ID)
//                .queryParam("redirect_uri", REDIRECT_URI)
//                .queryParam("response_type", "token")
//                .queryParam("scope", SCOPES)
//                .queryParam("prompt", "consent")
//                .queryParam("login", login)
//                .queryParam("password", passwordForTests)
//                .when()
//                .get(OAUTH_ENDPOINT);
//
//        Allure.step("Validating that the response status code is 200.");
//        assertThat(response.getStatusCode(), is(200));
//    }
//
//    @Test(testName = "Test OAuth endpoint without Client ID")
//    @Description("This test verifies that an error response is returned when making a request to the OAuth endpoint without a Client ID.")
//    public void testOAuthWithoutClientId_ShouldReturnError() {
//        Response response = given()
//                .queryParam("redirect_uri", REDIRECT_URI)
//                .queryParam("response_type", "token")
//                .queryParam("scope", SCOPES)
//                .queryParam("prompt", "consent")
//                .when()
//                .get(OAUTH_ENDPOINT);
//
//        Allure.step("Validating that the response status code is 400 and the body contains 'invalid_request'.");
//        assertThat(response.getStatusCode(), is(400));
//        assertThat(response.getBody().asString(), containsString("invalid_request"));
//    }
//
//    @Test(testName = "Test Google User Info API with valid access token")
//    @Description("This test verifies the Google User Info API response when using a valid access token.")
//    public void testGoogleUserInfoApi_ValidAccessToken() {
//        String accessToken = generateAccessToken(login, passwordForTests);
//
//        Response response = given()
//                .header("Authorization", "Bearer " + accessToken)
//                .when()
//                .get(GOOGLE_USER_INFO_API);
//
//        Allure.step("Validating the response status code is 200, and user data includes email and name.");
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(response.jsonPath().getString("email"), notNullValue());
//        assertThat(response.jsonPath().getString("name"), notNullValue());
//    }
//
//    @Test(testName = "Test missing Client ID environment variable")
//    @Description("This test simulates the presence of a missing Client ID in the environment variables.")
//    public void testMissingEnvironmentVariable_ClientId() {
//        String fakeClientId = System.getenv("FAKE_MISSING_CLIENT_ID");
//
//        Allure.step("Asserting that the fake Client ID environment variable is null.");
//        assertThat("Client ID should be null", fakeClientId, is(nullValue()));
//    }
//
//    @Test(testName = "Test invalid access token in Google User Info API")
//    @Description("This test ensures that invalid access tokens return a 401 status from the Google User Info API.")
//    public void testGoogleUserInfoApi_InvalidAccessToken() {
//        String invalidToken = "invalid_access_token_example";
//
//        Response response = given()
//                .header("Authorization", "Bearer " + invalidToken)
//                .when()
//                .get(GOOGLE_USER_INFO_API);
//
//        Allure.step("Validating the status is 401 and error message contains 'invalid_token'.");
//        assertThat(response.getStatusCode(), is(401));
//        assertThat(response.jsonPath().getString("error"), containsString("invalid_token"));
//    }
//
//    @Step("Generate OAuth access token for: {login}")
//    private String generateAccessToken(String login, String password) {
//        if ("user1".equals(login) && "pass123".equals(password)) {
//            return "valid_access_token_user1";
//        } else if ("user2@example.com".equals(login) && "password456".equals(password)) {
//            return "valid_access_token_user2";
//        }
//        return "invalid_token";
//    }
//
//    @Test(testName = "Test missing access token in Google User Info API")
//    @Description("This test ensures that requests without an access token to Google User Info API return 401.")
//    public void testGoogleUserInfoApi_MissingAccessToken() {
//        Response response = given()
//                .when()
//                .get(GOOGLE_USER_INFO_API);
//
//        Allure.step("Checking response returns a status code of 401 and the error indicates 'unauthorized'.");
//        assertThat(response.getStatusCode(), is(401));
//        assertThat(response.getBody().asString(), containsString("unauthorized"));
//    }
//
//    @Test(testName = "Extract error from redirect URI")
//    @Description("This test validates error parameter extraction from the redirect URI.")
//    public void testExtractErrorFromRedirectUri() {
//        String redirectUriWithError = REDIRECT_URI + "?error=access_denied";
//
//        String errorParam = extractErrorFromUrl(redirectUriWithError);
//
//        Allure.step("Validating that the extracted error matches 'access_denied'.");
//        assertThat(errorParam, equalTo("access_denied"));
//    }
//
//    @Step("Send OAuth request with necessary parameters")
//    private Response sendOAuthRequest(String clientId, String redirectUri) {
//        return given()
//                .queryParam("client_id", clientId)
//                .queryParam("redirect_uri", redirectUri)
//                .queryParam("response_type", "token")
//                .queryParam("scope", SCOPES)
//                .queryParam("prompt", "consent")
//                .when()
//                .get(OAUTH_ENDPOINT);
//    }
//
//    @Step("Extract error parameter from URL: {url}")
//    private String extractErrorFromUrl(String url) {
//        int errorIndex = url.indexOf("error=");
//        if (errorIndex >= 0) {
//            return url.substring(errorIndex + 6);
//        }
//        return null;
//    }
//
//}