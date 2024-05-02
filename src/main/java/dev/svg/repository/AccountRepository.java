package dev.svg.repository;

import dev.svg.model.account.Account;
import dev.svg.model.account.CheckingAccount;
import dev.svg.model.account.SavingAccount;
import dev.svg.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findAccountsByCustomers(List<Customer> customers);

    @Query("SELECT sa FROM Account sa WHERE TYPE(sa) = SavingAccount")
    List<SavingAccount> findAllSavingAccounts();

    @Query("SELECT ca FROM Account ca WHERE TYPE(ca) = CheckingAccount")
    List<CheckingAccount> findAllCheckingAccounts();

}
