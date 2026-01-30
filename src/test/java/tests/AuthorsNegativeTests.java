package tests;

import clients.AuthorsClient;
import io.qameta.allure.*;
import models.AuthorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Negative test suite for Author Management API.
 */
@Epic("Online Bookstore")
@Feature("Author Management - Negative Scenarios")
public class AuthorsNegativeTests extends BaseTest {
    private final AuthorsClient authorsClient = new AuthorsClient();

    @ParameterizedTest(name = "Scenario: {0} with ID: {1}")
    @CsvSource({
            "Non-existent ID, 999999",
            "Negative ID, -1",
            "Zero ID, 0"
    })
    @Severity(SeverityLevel.NORMAL)
    @Story("Retrieve specific author with invalid IDs")
    @DisplayName("Negative testing for Get Author by ID")
    public void testGetAuthorInvalidIds(String scenario, int id) {
        authorsClient.getById(id)
                .then()
                .log().ifValidationFails()
                .statusCode(404);
    }

    //The test fails because the FakeRestAPI implementation is non-persistent and lacks server-side validation,
    // returning 200 OK instead of the expected 404 Bad Request for invalid input.
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Update author details")
    @DisplayName("Error when updating author that does not exist")
    public void testUpdateNonExistentAuthor() {
        int nonExistentId = 888888;
        AuthorDto updateData = AuthorDto.builder()
                .firstName("No")
                .lastName("One")
                .build();

        authorsClient.update(nonExistentId, updateData)
                .then()
                .statusCode(404);
    }

    //The test fails because the FakeRestAPI implementation is non-persistent and lacks server-side validation,
    // returning 200 OK instead of the expected 400 Bad Request for invalid input.
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete author")
    @DisplayName("Error when deleting author with negative ID")
    public void testDeleteAuthorWithNegativeId() {
        authorsClient.delete(-1)
                .then()
                .statusCode(400);
    }

}