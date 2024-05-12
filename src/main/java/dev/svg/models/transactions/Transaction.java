package dev.svg.models.transactions;

import dev.svg.models.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(nullable = false, columnDefinition = "VARCHAR(25) DEFAULT 'DEBIT'")
    private TransactionType type;

    @Enumerated
    @Column(nullable = false)
    private TransactionAction action;

    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")//, insertable = false, updatable = false)
    private Account account;
}
