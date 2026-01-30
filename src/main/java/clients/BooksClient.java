package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.BookDto;

/**
 * Implementation of API client for Book-related operations.
 * Extends BaseClient for shared request specifications.
 */
public class BooksClient extends BaseClient implements CrudOperations<BookDto>{
    private static final String BOOKS_ENDPOINT = "/api/v1/Books";

    @Override
    @Step("Retrieve all books")
    public Response getAll() {
        return getRequestSpec()
                .get(BOOKS_ENDPOINT);
    }

    @Override
    @Step("Create a new book")
    public Response create(BookDto book) {
        return getRequestSpec()
                .body(book)
                .post(BOOKS_ENDPOINT);
    }

    @Override
    @Step("Get book by ID: {id}")
    public Response getById(int id) {
        return getRequestSpec()
                .pathParam("id", id)
                .get(BOOKS_ENDPOINT + "/{id}");
    }

    @Override
    @Step("Update book by ID: {id}")
    public Response update(int id, BookDto book) {
        return getRequestSpec()
                .pathParam("id", id)
                .body(book)
                .put(BOOKS_ENDPOINT + "/{id}");
    }

    @Override
    @Step("Delete book by ID: {id}")
    public Response delete(int id) {
        return getRequestSpec()
                .pathParam("id", id)
                .delete(BOOKS_ENDPOINT + "/{id}");
    }

}
