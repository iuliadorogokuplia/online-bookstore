package clients;

import io.restassured.response.Response;
import models.AuthorDTO;

public class AuthorsClient extends BaseClient implements CrudOperations<AuthorDTO>{
    private static final String AUTHORS_ENDPOINT = "/api/v1/Authors";
    private static final String AUTHOR_BY_BOOK_ENDPOINT = AUTHORS_ENDPOINT + "/authors/books";

    @Override
    public Response getAll() {
        return getRequestSpec()
                .get(AUTHORS_ENDPOINT);
    }

    @Override
    public Response create(AuthorDTO author) {
        return getRequestSpec()
                .body(author)
                .post(AUTHORS_ENDPOINT);
    }

    public Response getAuthorByBook(int idBook) {
        return getRequestSpec()
                .pathParam("id", idBook)
                .get(AUTHOR_BY_BOOK_ENDPOINT + "/{id}");
    }

    @Override
    public Response getById(int id) {
        return getRequestSpec()
                .pathParam("id", id)
                .get(AUTHORS_ENDPOINT + "/{id}");
    }

    @Override
    public Response update(int id, AuthorDTO author) {
        return getRequestSpec()
                .pathParam("id", id)
                .body(author)
                .put(AUTHORS_ENDPOINT + "/{id}");
    }

    @Override
    public Response delete(int id) {
        return getRequestSpec()
                .pathParam("id", id)
                .delete(AUTHORS_ENDPOINT + "/{id}");
    }

}
