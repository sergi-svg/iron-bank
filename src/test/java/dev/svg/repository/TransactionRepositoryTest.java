package dev.svg.repository;

import dev.svg.models.account.CheckingAccount;
import dev.svg.models.account.SavingAccount;
import dev.svg.models.customer.Address;
import dev.svg.models.customer.Customer;
import dev.svg.models.customer.Name;
import dev.svg.models.transaction.Transaction;
import dev.svg.models.transaction.TransactionAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    static Address address;
    static Address secondaryAddress;
    static Name name;
    static Customer customer;
    List<Customer> customers = new ArrayList<>();
    CheckingAccount checkingAccount;
    SavingAccount savingAccount;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setCity("Barcelona");
        address.setPostalCode("08001");
        address.setStreet("Gran via c.c");
        address.setStreetNumber("123");

        secondaryAddress = new Address();
        secondaryAddress.setCity("Tossa de Mar");
        secondaryAddress.setPostalCode("17320");
        secondaryAddress.setStreet("Carrer del catell");
        secondaryAddress.setStreetNumber("10");

        name = new Name();
        name.setName("Jordi");
        name.setSurname("Culé");

        customer = new Customer(
                "12345678A",
                "12345678A",
                name,
                address,
                secondaryAddress,
                "cule.jordi@hotmail.com",
                "600102030");

        customerRepository.save(customer);
        customers.add(customer);

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051111");
        checkingAccount.setBalance(new BigDecimal(1500));
        checkingAccount.setInterestRate(1.5);
        checkingAccount.setCustomers(customers);
        accountRepository.save(checkingAccount);

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("ES9121000418450200052222");
        savingAccount.setBalance(new BigDecimal(20500));
        savingAccount.setInterestRate(2.5);
        savingAccount.setCustomers(customers);
        accountRepository.save(savingAccount);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a transaction for checking account")
    void testCreateTransactionForCheckingAccount() {
        Transaction transaction = new Transaction(1L, TransactionAction.DEPOSIT, new BigDecimal(100), "Bizum received", LocalDateTime.now(), checkingAccount);
        transactionRepository.save(transaction);

        assertFalse(transactionRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Should create a transaction for saving account")
    void testCreateTransactionForSavingAccount() {
        Transaction transaction = new Transaction(
                1L,
                TransactionAction.DEPOSIT,
                new BigDecimal(100),
                "Bizum received",
                LocalDateTime.now(),
                checkingAccount);

        transactionRepository.save(transaction);

        assertFalse(transactionRepository.findAll().isEmpty());
    }

    @Test
    void findAllByDate() {
        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction(
                1L,
                TransactionAction.DEPOSIT,
                new BigDecimal(100),
                "Bizum received",
                LocalDateTime.now(),
                checkingAccount);

        transactionRepository.save(transaction);

        assertFalse(transactionRepository.findAllByDate(date).isEmpty());
    }

    @Test
    void findAllByActionAndDate() {
        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction(
                1L,
                TransactionAction.DEPOSIT,
                new BigDecimal(100),
                "Bizum received",
                date,
                checkingAccount);

        transactionRepository.save(transaction);

        assertFalse(transactionRepository.findAllByActionAndDate(TransactionAction.DEPOSIT, date).isEmpty());
    }

    @Test
    void findAllByActionAndDateBetween() {
        LocalDateTime  currentDate = LocalDateTime.now();
        LocalDateTime  dateOneDayBefore = currentDate.minusDays(1);
        LocalDateTime  dateOneDayAfter = currentDate.plusDays(1);

        Transaction transaction1 = new Transaction(
                1L,
                TransactionAction.DEPOSIT,
                new BigDecimal(100),
                "Bizum received",
                currentDate,
                checkingAccount);

        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction(
                2L,
                TransactionAction.DEPOSIT,
                new BigDecimal(200),
                "Bizum received",
                currentDate,
                checkingAccount);

        transactionRepository.save(transaction2);

        assertFalse(transactionRepository.findAllByActionAndDateBetween(TransactionAction.DEPOSIT, dateOneDayBefore, dateOneDayAfter).isEmpty());
    }
}