package dev.svg.services;

import dev.svg.model.account.Account;
import dev.svg.model.account.CheckingAccount;
import dev.svg.model.account.SavingAccount;
import dev.svg.model.customer.Customer;
import dev.svg.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<SavingAccount> getAllSavingAccount() {
        return accountRepository.findAllSavingAccounts();
    }

    public List<CheckingAccount> getAllCheckingAccount() {
        return accountRepository.findAllCheckingAccounts();
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public List<Account> getAllAccountsByCity(String city) {
        return accountRepository.findAccountsByCity(city);
    }

    public List<Account> getAllAccountsByPostalCode(String postalCode) {
        return accountRepository.findAccountsByPostalCode(postalCode);
    }

    public Account createAccount(Account account) {
        account.getCustomers().forEach(c -> c.addToAccounts(account));
        return accountRepository.save(account);
    }

    public Account updateAccount(String accountNumber, Account account) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            account.setAccountNumber(optionalAccount.get().getAccountNumber());
            return accountRepository.save(account);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteByAccountNumber(String accountNumber) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(optionalAccount.get().getAccountNumber());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteAllAccount() {
        accountRepository.deleteAll();
    }

}
