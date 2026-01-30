import clients.AuthorsClient;
import io.qameta.allure.*;
import models.AuthorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;


@Epic("Online Bookstore")
@Feature("Author Management - Negative Scenarios")
public class AuthorsNegativeTests extends BaseTest {
    private final AuthorsClient authorsClient = new AuthorsClient();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Retrieve specific author")
    @DisplayName("Error when retrieving author with non-existent ID")
    public void testGetNonExistentAuthor() {
        int nonExistentId = 999999;
        authorsClient.getById(nonExistentId)
                .then()
                .statusCode(404);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Create a new author")
    @DisplayName("Error when creating author with empty required fields")
    public void testCreateAuthorWithEmptyData() {
        AuthorDTO emptyAuthor = AuthorDTO.builder()
                .id(0)
                .firstName("")
                .lastName("")
                .build();

        authorsClient.create(emptyAuthor)
                .then()
                .statusCode(400);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Update author details")
    @DisplayName("Error when updating author that does not exist")
    public void testUpdateNonExistentAuthor() {
        int nonExistentId = 888888;
        AuthorDTO updateData = AuthorDTO.builder()
                .firstName("No")
                .lastName("One")
                .build();

        authorsClient.update(nonExistentId, updateData)
                .then()
                .statusCode(404);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete author")
    @DisplayName("Error when deleting author with negative ID")
    public void testDeleteAuthorWithNegativeId() {
        authorsClient.delete(-1)
                .then()
                .statusCode(400);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Create a new author")
    @DisplayName("Error when sending oversized data in fields")
    public void testCreateAuthorWithVeryLongNames() {
        String longName = "A".repeat(2000);
        AuthorDTO hugeAuthor = AuthorDTO.builder()
                .id(ThreadLocalRandom.current().nextInt(1000, 9000))
                .firstName(longName)
                .lastName(longName)
                .build();

        authorsClient.create(hugeAuthor)
                .then()
                .statusCode(400);
    }
}