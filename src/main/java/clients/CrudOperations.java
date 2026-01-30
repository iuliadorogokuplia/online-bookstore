package clients;

import io.restassured.response.Response;

public interface CrudOperations<T> {
    Response getAll();
    Response getById(int id);
    Response create(T body);
    Response update(int id, T body);
    Response delete(int id);
}
