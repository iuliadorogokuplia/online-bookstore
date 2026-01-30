package tests;

import clients.BooksClient;
import io.qameta.allure.*;
import models.BookDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.*;

/**
 * Positive test suite for Books Management API.
 */
@Epic("Online Bookstore")
@Feature("Books Management - Negative Scenarios")
public class BooksNegativeTests extends BaseTest{
    private final BooksClient booksClient = new BooksClient();

    @ParameterizedTest(name = "Scenario: {0} with ID: {1}")
    @CsvSource({
            "Non-existent ID, 999999",
            "Negative ID, -1",
            "Zero ID, 0"
    })
    @Severity(SeverityLevel.NORMAL)
    @Story("Get book by invalid IDs")
    @DisplayName("Negative: Retrieve book with non-existent or boundary IDs")
    public void testGetBookByInvalidId(String description, int invalidId) {
        booksClient.getById(invalidId)
                .then()
                .statusCode(404)
                .log().ifValidationFails();
    }

    @ParameterizedTest(name = "Invalid Date: {0}")
    @ValueSource(strings = {"not-a-date", "2024-13-45", "99-99-9999", ""})
    @Severity(SeverityLevel.NORMAL)
    @Story("Create book with invalid date format")
    @DisplayName("Negative: Create book with malformed publishDate")
    public void testCreateBookWithInvalidDate(String invalidDate) {
        BookDto invalidBook = BookDto.builder()
                .title("Edge Case Date")
                .publishDate(invalidDate)
                .build();
        booksClient.create(invalidBook)
                .then()
                .statusCode(400)
                .log().ifValidationFails();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Security - SQL Injection")
    @DisplayName("Security: SQL Injection attempt in book title")
    public void testSqlInjectionInTitle() {
        BookDto sqlInjectionBook = BookDto.builder()
                .title("'; DROP TABLE Books; --")
                .description("Security Risk Test")
                .build();
        booksClient.create(sqlInjectionBook)
                .then()
                .statusCode(400);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Boundary Value - Extremely Long Title")
    @DisplayName("Negative: Create book with 5000 character title")
    public void testLongTitleBoundary() {
        String longTitle = "A".repeat(5000);
        BookDto book = BookDto.builder()
                .title(longTitle)
                .description("Testing payload size limits")
                .build();
        booksClient.create(book)
                .then()
                .statusCode(400);
    }

    //The test fails because the FakeRestAPI implementation is non-persistent and lacks server-side validation,
    // returning 200 OK instead of the expected 400 Bad Request for invalid input.
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Empty Request Body")
    @DisplayName("Negative: Create book with empty JSON body")
    public void testCreateBookWithEmptyBody() {
        io.restassured.RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{}")
                .post(utils.ConfigReader.getBaseUrl() + "/api/v1/Books")
                .then()
                .statusCode(400);
    }
}