package dev.svg.repository;

import dev.svg.model.account.CheckingAccount;
import dev.svg.model.account.SavingAccount;
import dev.svg.model.customer.Address;
import dev.svg.model.customer.Customer;
import dev.svg.model.customer.Name;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;

    static Address address;
    static Address secondaryAddress;
    static Name name;
    static Customer customer;
    List<Customer> customers = new ArrayList<>();

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
                name,
                address,
                secondaryAddress,
                "cule.jordi@hotmail.com",
                "600102030", null);

        customerRepository.save(customer);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new checking account")
    void testCreateNewCheckingAccount() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);

        accountRepository.save(checkingAccount);

        assertNotNull(accountRepository.findByAccountNumber("ES9121000418450200051332"));
    }

    @Test
    @DisplayName("Should create a new saving account")
    void testCreateNewSavingAccount() {
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("ES9121000418450200051111");
        savingAccount.setBalance(10500);
        savingAccount.setInterestRate(2.5);

        customers.add(customer);
        savingAccount.setCustomers(customers);

        accountRepository.save(savingAccount);

        assertNotNull(accountRepository.findByAccountNumber("ES9121000418450200051111"));
    }

    @Test
    @DisplayName("Should delete an account by its account number")
    void testDeleteByAccountNumber() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);

        accountRepository.save(checkingAccount);
        assertFalse(accountRepository.findByAccountNumber("ES9121000418450200051332").isEmpty());

        accountRepository.deleteById("ES9121000418450200051332");
        assertTrue(accountRepository.findByAccountNumber("ES9121000418450200051332").isEmpty());
    }

    @Test
    @DisplayName("Should return an account searching by account number")
    void testFindByAccountNumber() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);

        accountRepository.save(checkingAccount);

        assertNotNull(accountRepository.findByAccountNumber("ES9121000418450200051332"));
    }

    @Test
    @DisplayName("Should return a list of accounts by city")
    void testFindAccountsByCity() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);

        accountRepository.save(checkingAccount);

        assertFalse(accountRepository.findAccountsByCity("Barcelona").isEmpty());
    }

    @Test
    @DisplayName("Should return a list of accounts by postalCode")
    void testFindAccountsByPostalCode() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);

        accountRepository.save(checkingAccount);

        assertFalse(accountRepository.findAccountsByPostalCode("08001").isEmpty());
    }

    @Test
    @DisplayName("Should return a list of customer's accounts")
    void testFindAccountsByCustomers() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);

        accountRepository.save(checkingAccount);

        assertFalse(accountRepository.findAccountsByCustomers(customers).isEmpty());
    }

    @Test
    @DisplayName("Should retrieve all saving accounts")
    void testFindAllSavingAccounts() {
        // Create a saving account
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("ES9121000418450200051111");
        savingAccount.setBalance(10500);
        savingAccount.setInterestRate(2.5);

        customers.add(customer);
        savingAccount.setCustomers(customers);
        accountRepository.save(savingAccount);

        // Create a checking account
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);
        accountRepository.save(checkingAccount);

        int expectedAccount = 1;
        assertEquals(expectedAccount, accountRepository.findAllSavingAccounts().size());
    }

    @Test
    @DisplayName("Should retrieve all checking accounts")
    void testFindAllCheckingAccounts() {
        // Create a saving account
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("ES9121000418450200051111");
        savingAccount.setBalance(10500);
        savingAccount.setInterestRate(2.5);

        customers.add(customer);
        savingAccount.setCustomers(customers);
        accountRepository.save(savingAccount);

        // Create a checking account
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);
        accountRepository.save(checkingAccount);

        int expectedAccount = 1;
        assertEquals(expectedAccount, accountRepository.findAllCheckingAccounts().size());
    }


    @ParameterizedTest
    @DisplayName("Should retrieve all accounts with a balance bigger concrete values")
    @ValueSource(doubles = {500, 1000, 100})
    void testFindAllAccountsHavingBiggerBalanceThanValue(double values) {
        // Create a saving account
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("ES9121000418450200051111");
        savingAccount.setBalance(10500);
        savingAccount.setInterestRate(2.5);

        customers.add(customer);
        savingAccount.setCustomers(customers);
        accountRepository.save(savingAccount);

        // Create a checking account
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        customers.add(customer);
        checkingAccount.setCustomers(customers);
        accountRepository.save(checkingAccount);

        assertFalse(accountRepository.findAllAccountsHavingBiggerBalanceThanValue(values).isEmpty());
    }

}