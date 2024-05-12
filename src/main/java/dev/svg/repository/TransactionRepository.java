package dev.svg.repository;

import dev.svg.models.transactions.Transaction;
import dev.svg.models.transactions.TransactionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.date = :date")
    List<Transaction> findAllByDate(Date date);

    List<Transaction> findAllByActionAndDate(TransactionAction action, Date date);

    List<Transaction> findAllByActionAndDateBetween(TransactionAction action, Date startDate, Date endDate);

}
