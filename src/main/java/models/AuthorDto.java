package models;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object representing an Author in the Online Bookstore system.
 * Used for serializing and deserializing API request/response bodies.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private int id;
    private int idBook;
    private String firstName;
    private String lastName;
}
