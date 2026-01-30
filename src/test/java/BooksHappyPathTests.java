import clients.BooksClient;
import io.qameta.allure.*;
import io.restassured.common.mapper.TypeRef;
import models.BookDTO;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

public class BooksHappyPathTests extends BaseTest{
    private final BooksClient booksClient = new BooksClient();

    private int newCreatedUpdatedBookId;
    private final int existedBookId = 3;
    BookDTO newCreatedUpdatedBook;

    BookDTO existedBook = BookDTO.builder()
            .id(3)
            .title("Book 3")
            .pageCount(300)
            .build();

    @BeforeEach
    void setUp() {
        //In a real-world production environment, relying on static data (like hardcoded IDs) makes tests 'brittle'
        // and prone to failure if the database state changes.
        // To ensure test isolation and reliability, it is best practice to avoid static constants.
        // Better to create dynamically a new book during the setup phase
        // use its unique ID throughout the test,
        // and ideally clean it up afterwards.
        // This ensures that each test is independent and repeatable.
        //In the context of FakeRestAPI, since the server doesn't actually persist (save) new records permanently to a real database,
        //I am essentially working with "mocked" or "volatile" data.
        newCreatedUpdatedBookId = ThreadLocalRandom.current().nextInt(10000, 100000);
        newCreatedUpdatedBook = BookDTO.builder()
                .id(newCreatedUpdatedBookId)
                .title("Clean Architecture")
                .description("Solid principles in practice")
                .publishDate(LocalDate.now().toString())
                .excerpt("Test User")
                .pageCount(400)
                .build();
    }

    @Test
    @DisplayName("Add a new book to the system")
    public void testCreateBook() {
        BookDTO actualBook = booksClient.create(newCreatedUpdatedBook)
                .then().statusCode(200)
                .extract().as(BookDTO.class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualBook)
                .usingRecursiveComparison()
                .ignoringFields("publishDate")
                .isEqualTo(newCreatedUpdatedBook);
        softly.assertThat(actualBook.getPublishDate())
                .as("Verify that publishDate contains today's date")
                .contains(newCreatedUpdatedBook.getPublishDate());
        softly.assertAll();
    }

    @Test
    @DisplayName("Update an existing book by its ID")
    public void testUpdateBook() {
        BookDTO actualBook = booksClient.update(existedBookId, newCreatedUpdatedBook)
                .then().statusCode(200)
                .extract().as(BookDTO.class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualBook)
                .usingRecursiveComparison()
                .ignoringFields("publishDate")
                .isEqualTo(newCreatedUpdatedBook);
        softly.assertThat(actualBook.getPublishDate())
                .as("Verify that publishDate contains today's date")
                .contains(newCreatedUpdatedBook.getPublishDate());
        softly.assertAll();
    }

    @Test
    @DisplayName("Delete a book by its ID")
    public void testDeleteBook() {
        booksClient.delete(existedBookId).then().statusCode(200);
    }

    @Test
    @DisplayName("Retrieve details of a specific book by its ID")
    public void testGetBook() {
        BookDTO actualBook = booksClient.getById(existedBookId)
                .then().statusCode(200)
                .extract().as(BookDTO.class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualBook.getId())
                .as("Verify id")
                .isEqualTo(existedBookId);
        softly.assertThat(actualBook.getTitle())
                .as("Verify Title")
                .isEqualTo(existedBook.getTitle());
        softly.assertThat(actualBook.getDescription())
                .as("Verify Description")
                .isNotEmpty();
        softly.assertThat(actualBook.getPublishDate())
                .as("Verify Publish Date")
                .isNotEmpty();
        softly.assertThat(actualBook.getExcerpt())
                .as("Verify Excerpt")
                .isNotEmpty();
        softly.assertThat(actualBook.getPageCount())
                .as("Verify Page Count")
                .isEqualTo(existedBook.getPageCount());
        softly.assertAll();
    }

    @Test
    @DisplayName("Retrieve a list of all books")
    public void testGetAllBooks() {
        List<BookDTO> books = booksClient.getAll()
                .then().statusCode(200).extract().as(new TypeRef<List<BookDTO>>() {
                });
        assertThat(books)
                .as("List of books from API")
                .hasSizeGreaterThan(1)
                .allSatisfy(book -> {
                    SoftAssertions softly = new SoftAssertions();
                    softly.assertThat(book.getId())
                            .as("Book ID for " + book.getTitle())
                            .isPositive();
                    softly.assertThat(book.getTitle())
                            .as("Title for book ID: " + book.getId())
                            .isNotBlank();
                    softly.assertThat(book.getDescription())
                            .as("Description for book ID: " + book.getId())
                            .isNotBlank();
                    softly.assertThat(book.getPageCount())
                            .as("Page count for book ID: " + book.getId())
                            .isGreaterThan(0);
                    softly.assertThat(book.getExcerpt())
                            .as("Excerpt for book ID: " + book.getId())
                            .isNotBlank();
                    softly.assertAll();
                });
    }

    @AfterEach
    void cleanUp() {
        booksClient.delete(newCreatedUpdatedBookId)
                .then()
                .statusCode(200);
    }

}
