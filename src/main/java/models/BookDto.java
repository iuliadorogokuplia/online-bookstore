package models;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object representing an Book in the Online Bookstore system.
 * Used for serializing and deserializing API request/response bodies.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private int id;
    private String title;
    private String description;
    private int pageCount;
    private String excerpt;
    private String publishDate;
}
