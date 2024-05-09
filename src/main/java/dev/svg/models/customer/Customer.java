package dev.svg.models.customer;

import dev.svg.models.account.Account;
import dev.svg.security.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer extends User {

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
            joinColumns = @JoinColumn(name = "id_card"),
            inverseJoinColumns = @JoinColumn(name = "account_number")
    )
    private List<Account> accounts = new ArrayList<>();

    public Customer(String idCard, String password, Name name, Address address, Address secondaryAddress, String mail, String number, List<Account> accounts) {
        super(idCard, password, null);
        this.name = name;
        this.address = address;
        this.secondaryAddress = secondaryAddress;
        this.email = mail;
        this.phone = number;
    }

}
