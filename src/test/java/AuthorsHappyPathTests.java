import clients.AuthorsClient;
import io.restassured.common.mapper.TypeRef;
import models.AuthorDTO;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorsHappyPathTests extends BaseTest {
    private final AuthorsClient authorsClient = new AuthorsClient();
    private int newCreatedUpdatedAuthorId;
    private final int existedAuthorId = 3;
    AuthorDTO newCreatedUpdatedAuthor;
    AuthorDTO existedAuthor = AuthorDTO.builder()
            .id(3)
            .idBook(1)
            .firstName("First Name 3")
            .lastName("Last Name 3")
            .build();

    @BeforeEach
    void setUp() {
        //In a real-world production environment, relying on static data (like hardcoded IDs) makes tests 'brittle'
        // and prone to failure if the database state changes.
        // To ensure test isolation and reliability, it is best practice to avoid static constants.
        // Better to create dynamically a new authorDTO during the setup phase
        // use its unique ID throughout the test,
        // and ideally clean it up afterwards.
        // This ensures that each test is independent and repeatable.
        //In the context of FakeRestAPI, since the server doesn't actually persist (save) new records permanently to a real database,
        //I am essentially working with "mocked" or "volatile" data.
        newCreatedUpdatedAuthorId = ThreadLocalRandom.current().nextInt(10000, 100000);
        newCreatedUpdatedAuthor = AuthorDTO.builder()
                .id(newCreatedUpdatedAuthorId)
                .idBook(2)
                .firstName("Tested First Name")
                .lastName("Tested Last Name")
                .build();
    }

    @Test
    @DisplayName("Add a new author to the system")
    public void testCreateAuthor() {
        AuthorDTO actualAuthor = authorsClient.create(newCreatedUpdatedAuthor)
                .then().statusCode(200)
                .extract().as(AuthorDTO.class);
        assertEquals(actualAuthor, newCreatedUpdatedAuthor);
    }

    @Test
    @DisplayName("Update an existing author’s details")
    public void testUpdateAuthor() {
        AuthorDTO actualAuthor = authorsClient.update(existedAuthorId, newCreatedUpdatedAuthor)
                .then().statusCode(200)
                .extract().as(AuthorDTO.class);
        assertEquals(actualAuthor, newCreatedUpdatedAuthor);
    }

    @Test
    @DisplayName("Delete an author by their ID")
    public void testDeleteAuthor() {
        authorsClient.delete(existedAuthorId).then().statusCode(200);
    }

    @Test
    @DisplayName("Retrieve details of a specific author by their ID.")
    public void testGetBook() {
        AuthorDTO actualAuthor = authorsClient.getById(existedAuthorId)
                .then().statusCode(200)
                .extract().as(AuthorDTO.class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualAuthor.getId())
                .as("Verify id")
                .isEqualTo(existedAuthorId);
        softly.assertThat(actualAuthor.getIdBook())
                .as("Verify Id Book")
                .isEqualTo(existedAuthor.getIdBook());
        softly.assertThat(actualAuthor.getFirstName())
                .as("Verify First Name")
                .isEqualTo(existedAuthor.getFirstName());
        softly.assertThat(actualAuthor.getLastName())
                .as("Verify Last Name")
                .isEqualTo(existedAuthor.getLastName());
        softly.assertAll();
    }

    @Test
    @DisplayName("Retrieve a list of all authors.")
    public void testGetAllAuthors() {
        List<AuthorDTO> authors = authorsClient.getAll()
                .then().statusCode(200).extract().as(new TypeRef<List<AuthorDTO>>() {
                });
        assertThat(authors)
                .as("List of author from API")
                .hasSizeGreaterThan(1)
                .allSatisfy(author -> {
                    SoftAssertions softly = new SoftAssertions();
                    softly.assertThat(author.getId())
                            .as("Author ID" + author.getId())
                            .isPositive();
                    softly.assertThat(author.getIdBook())
                            .as("Book ID" + author.getIdBook())
                            .isPositive();
                    softly.assertThat(author.getLastName())
                            .as("Last Name " + author.getLastName())
                            .isNotBlank();
                    softly.assertThat(author.getFirstName())
                            .as("First Name " + author.getFirstName())
                            .isNotBlank();
                    softly.assertAll();
                });
    }

    @AfterEach
    void cleanUp() {
        authorsClient.delete(newCreatedUpdatedAuthorId)
                .then()
                .statusCode(200);
    }

}
