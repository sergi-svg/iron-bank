package dev.svg.model.account;

import dev.svg.model.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Account {

    @Id
    @Column(name = "account_number")
    @Size(min = 24, max = 24)
    private String accountNumber;

    private double balance;

    @Column(name = "interest_rate")
    @Min(value = 0, message = "Interest rate must be greater than or equal to 0")
    private double interestRate;

    @Column(name = "account_type", columnDefinition = "VARCHAR(10) DEFAULT 'SAVINGS'")
    @Enumerated
    private AccountType accountType;

    @ManyToMany
    @JoinTable(name = "customer_account",
            joinColumns = @JoinColumn(name = "account_number"),
            inverseJoinColumns = @JoinColumn(name = "id_card"))
    private List<Customer> customers;

}
