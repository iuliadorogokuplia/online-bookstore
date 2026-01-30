package clients;

import io.restassured.response.Response;

/**
 * Generic interface for standard CRUD (Create, Read, Update, Delete) operations.
 * Ensures consistent naming and behavior across all API client implementations.
 *
 * @param <T> The Data Transfer Object (DTO) type used for the request body.
 */
public interface CrudOperations<T> {
    Response getAll();
    Response getById(int id);
    Response create(T body);
    Response update(int id, T body);
    Response delete(int id);
}
