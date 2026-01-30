package clients;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

/**
 * Base client class that provides a shared RequestSpecification for all API clients.
 * It handles base URL configuration, common headers, and Allure reporting integration.
 */
public class BaseClient {

    protected static final String BASE_URL = ConfigReader.getBaseUrl();

    protected RequestSpecification getRequestSpec() {
        return given()
                .filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .log().ifValidationFails();
    }
}
