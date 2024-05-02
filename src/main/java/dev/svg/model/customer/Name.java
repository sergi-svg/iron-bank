package dev.svg.model.customer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Embeddable
public class Name {

    @NotEmpty(message = "You must supply a product name")
    @Size(min = 1, max = 50, message = "Name length must be between 1 and 50 characters")
    private String name;

    @NotEmpty(message = "You must supply a product surname")
    @Size(min = 1, max = 100, message = "Surname length must be between 1 and 100 characters")
    private String surname;

}