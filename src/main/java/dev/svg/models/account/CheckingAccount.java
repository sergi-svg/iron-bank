package dev.svg.models.account;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("checking")
public class CheckingAccount extends Account {

    @Column(name = "interest_rate")
    @Min(value = 0, message = "Interest rate must be smaller than or equal to 2")
    private double interestRate;

}
