package dev.svg.repository;

import dev.svg.models.account.Account;
import dev.svg.models.account.CheckingAccount;
import dev.svg.models.account.SavingAccount;
import dev.svg.models.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT DISTINCT a FROM Account a " +
            "JOIN a.customers c " +
            "JOIN c.address addr " +
            "JOIN c.secondaryAddress secAddr " +
            "WHERE (addr.city = :city OR secAddr.city = :city)")
    List<Account> findAccountsByCity(String city);

    @Query("SELECT DISTINCT a FROM Account a " +
            "JOIN a.customers c " +
            "JOIN c.address addr " +
            "JOIN c.secondaryAddress secAddr " +
            "WHERE (addr.postalCode = :postalCode OR secAddr.postalCode = :postalCode)")
    List<Account> findAccountsByPostalCode(String postalCode);

    List<Account> findAccountsByCustomersIn(Collection<Customer> customers);

    @Query("SELECT sa FROM Account sa WHERE TYPE(sa) = SavingAccount")
    List<SavingAccount> findAllSavingAccounts();

    @Query("SELECT ca FROM Account ca WHERE TYPE(ca) = CheckingAccount")
    List<CheckingAccount> findAllCheckingAccounts();

    @Query("SELECT a FROM Account a WHERE a.balance > :value")
    List<Account> findAllAccountsHavingBiggerBalanceThanValue(double value);
}
