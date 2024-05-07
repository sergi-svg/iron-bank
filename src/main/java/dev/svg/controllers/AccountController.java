package dev.svg.controllers;

import dev.svg.model.account.Account;
import dev.svg.model.account.CheckingAccount;
import dev.svg.model.account.SavingAccount;
import dev.svg.services.AccountService;
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

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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

    @ResponseStatus(HttpStatus.OK)
    public void deleteAllResources() {

    }
}
