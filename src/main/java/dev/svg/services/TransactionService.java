package dev.svg.services;

import dev.svg.models.account.Account;
import dev.svg.models.transaction.Transaction;
import dev.svg.models.transaction.TransactionAction;
import dev.svg.repository.AccountRepository;
import dev.svg.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getTransactionsByDate(String accountNumber, LocalDateTime date) {
        return transactionRepository.findAllByDate(date);
    }

    public List<Transaction> getTransactionsByActionAndDate(TransactionAction action, LocalDateTime date) {
        return transactionRepository.findAllByActionAndDate(action, date);
    }

    public List<Transaction> getTransactionsByActionAndDateBetween(TransactionAction action, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findAllByActionAndDateBetween(action, startDate, endDate);
    }

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).get();
        if (accountRepository.findByAccountNumber(accountNumber).isPresent()) {
            return transactionRepository.findAllByAccount(account);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

    }

    public Transaction createTransaction(String accountNumber, Transaction transaction) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            transaction.setAccount(optionalAccount.get());
            return transactionRepository.save(transaction);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}


