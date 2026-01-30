package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.BookDTO;

public class BooksClient extends BaseClient implements CrudOperations<BookDTO>{
    private static final String BOOKS_ENDPOINT = "/api/v1/Books";

    @Override
    public Response getAll() {
        return getRequestSpec()
                .get(BOOKS_ENDPOINT);
    }

    @Override
    @Step("Creating a new book with ID: {book.id}")
    public Response create(BookDTO book) {
        return getRequestSpec()
                .body(book)
                .post(BOOKS_ENDPOINT);
    }

    @Override
    @Step("Getting book by ID: {id}")
    public Response getById(int id) {
        return getRequestSpec()
                .pathParam("id", id)
                .get(BOOKS_ENDPOINT + "/{id}");
    }

    @Override
    public Response update(int id, BookDTO book) {
        return getRequestSpec()
                .pathParam("id", id)
                .body(book)
                .put(BOOKS_ENDPOINT + "/{id}");
    }

    @Override
    public Response delete(int id) {
        return getRequestSpec()
                .pathParam("id", id)
                .delete(BOOKS_ENDPOINT + "/{id}");
    }

}
