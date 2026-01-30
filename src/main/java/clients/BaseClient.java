package clients;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

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
