package dev.svg.repository;

import dev.svg.models.transaction.Transaction;
import dev.svg.models.transaction.TransactionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.date = :date")
    List<Transaction> findAllByDate(LocalDateTime date);

    List<Transaction> findAllByActionAndDate(TransactionAction action, LocalDateTime date);

    List<Transaction> findAllByActionAndDateBetween(TransactionAction action, LocalDateTime startDate, LocalDateTime endDate);

}
