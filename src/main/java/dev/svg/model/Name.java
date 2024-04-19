package dev.svg.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Name {
    @Size(min = 1, max = 50, message = "Name length must be between 1 and 50 characters")
    private String name;

    @Size(min = 1, max = 100, message = "Surname length must be between 1 and 100 characters")
    private String surname;

}