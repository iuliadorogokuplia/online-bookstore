package models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private int id;
    private int idBook;
    private String firstName;
    private String lastName;
}
