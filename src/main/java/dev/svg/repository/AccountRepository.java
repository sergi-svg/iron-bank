package dev.svg.repository;

import dev.svg.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository <Account, String> {

    Optional<Account> findByAccountNumber(String accountNumber);

}
