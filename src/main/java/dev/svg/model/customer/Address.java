package dev.svg.model.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class Address {

    @NotNull
    private String city;
    @NotNull
    @Column(name = "postal_code")
    private String postalCode;
    @NotNull
    private String street;
    @NotNull
    @Column(name = "street_number")
    private String streetNumber;
}
