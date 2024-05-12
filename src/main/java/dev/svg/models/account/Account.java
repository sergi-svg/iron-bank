package dev.svg.models.account;

import dev.svg.models.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")
public class Account {

    @Id
    @Column(name = "account_number")
    @Size(min = 24, max = 24)
    private String accountNumber;

    private double balance;

    @ManyToMany
    @JoinTable(name = "customer_account",
            joinColumns = @JoinColumn(name = "account_number"),
            inverseJoinColumns = @JoinColumn(name = "id_card"))
    private List<Customer> customers;

}
