package dev.svg.model.account;

import jakarta.persistence.*;
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
@DiscriminatorValue("saving")
public class SavingAccount extends Account {

    @Column(name = "interest_rate")
    @Min(value = 0, message = "Interest rate must be greater than or equal to 0")
    private double interestRate;

}
