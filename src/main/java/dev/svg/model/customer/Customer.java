package dev.svg.model.customer;

import dev.svg.model.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_customer_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_customer_phone", columnNames = "phone")
        }
)
@Entity
public class Customer {

    @Id
    @Column(name = "id_card")
    @Size(min = 9, max = 9)
    private String idCard;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    @AttributeOverrides({
            @AttributeOverride(name="city", column = @Column(name = "secondary_city")),
            @AttributeOverride(name="postalCode", column = @Column(name = "secondary_postal_code")),
            @AttributeOverride(name="street", column = @Column(name = "secondary_street")),
            @AttributeOverride(name="streetNumber", column = @Column(name = "secondary_street_number"))
    })
    @Embedded
    private Address secondaryAddress;

    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "customer_account",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<Account> accounts;
}
