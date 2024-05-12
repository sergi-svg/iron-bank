package dev.svg.services;

import dev.svg.models.transactions.Transaction;
import dev.svg.models.transactions.TransactionAction;
import dev.svg.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsByDate(String accountNumber, Date date) {
        return transactionRepository.findAllByDate(date);
    }

    public List<Transaction> getTransactionsByActionAndDate(TransactionAction action, Date date) {
        return transactionRepository.findAllByActionAndDate(action, date);
    }

    public List<Transaction> getTransactionsByActionAndDateBetween(TransactionAction action, Date startDate, Date endDate) {
        return transactionRepository.findAllByActionAndDateBetween(action, startDate, endDate);
    }

}


