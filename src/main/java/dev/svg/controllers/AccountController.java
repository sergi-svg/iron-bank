package dev.svg.controllers;

import dev.svg.models.account.Account;
import dev.svg.models.account.CheckingAccount;
import dev.svg.models.account.SavingAccount;
import dev.svg.models.transaction.Transaction;
import dev.svg.services.AccountService;
import dev.svg.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/iron-bank")
public class AccountController implements ResourceController <Account> {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllResources() {
        return accountService.getAllAccounts();
    }

    @GetMapping(path = "/account")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Account> getResourceByParams(@RequestParam HashMap<String, String> params) {
        Optional<Account> account = Optional.empty();
        if (params.containsKey("accountNumber")) account = accountService.getAccountByAccountNumber(params.get("accountNumber"));

        return account;
    }

    @GetMapping(path = "/accounts_by")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getResourcesByParam(@RequestParam HashMap<String, String> params) {
        List<Account> accounts = new ArrayList<>();
        if (params.containsKey("city")) accounts = accountService.getAllAccountsByCity(params.get("city"));
        else if (params.containsKey("postalCode")) accounts = accountService.getAllAccountsByPostalCode(params.get("postalCode"));

        return accounts;
    }

    @GetMapping(path = "/accounts/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getCheckingAccounts() {
        return accountService.getAllCheckingAccount();
    }

    @GetMapping(path = "/accounts/saving")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingAccount> getSavingAccounts() {
        return accountService.getAllSavingAccount();
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createResource(@RequestBody @Valid Account account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Account updateResource(@PathVariable String id, @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResource(@PathVariable("id") String id) {
        accountService.deleteByAccountNumber(id);
    }

    @DeleteMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllResources() {
        accountService.deleteAllAccount();
    }

    @GetMapping("/accounts/{accountNumber}/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        return transactionService.getTransactionsByAccountNumber(accountNumber);
    }


    @PostMapping("/accounts/{accountNumber}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@PathVariable String accountNumber, @RequestBody @Valid Transaction transaction) {
        return transactionService.createTransaction(accountNumber, transaction);
    }


}
