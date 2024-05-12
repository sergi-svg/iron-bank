package dev.svg.services;

import dev.svg.models.transaction.Transaction;
import dev.svg.models.transaction.TransactionAction;
import dev.svg.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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

}


