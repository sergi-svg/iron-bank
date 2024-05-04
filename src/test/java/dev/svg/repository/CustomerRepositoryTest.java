package dev.svg.repository;

import dev.svg.model.customer.Address;
import dev.svg.model.customer.Customer;
import dev.svg.model.customer.Name;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    static Address address;
    static Address secondaryAddress;
    static Name name;
    static Customer customer;

    @BeforeAll
    static void setUp() {
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
                "600102030", null
        );
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new customer")
    void testCreateNewCustomer() {
        customerRepository.save(customer);
        assertNotNull(customerRepository.findById("12345678A"));
    }

    @Test
    @DisplayName("Should update the customer's email")
    void testUpdateCustomerEmail() {
        String expectedEmail = "new@email.dev";

        customer.setEmail(expectedEmail);
        customerRepository.save(customer);

        assertNotNull(customerRepository.findByEmail("expectedEmail"));
    }

    @Test
    @DisplayName("Should delete a customer by idCard")
    void testDeleteByIdCard() {
        customerRepository.save(customer);
        Optional<Customer> optionalCustomer = customerRepository.findByIdCard("12345678A");
        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById("12345678A");
        }

        assertTrue(customerRepository.findByIdCard("12345678A").isEmpty(),
                "Customer with id card 12345678A should not exist after deletion");
    }

    @Test
    @DisplayName("Should find a customer by email")
    void testFindByEmail() {
        customerRepository.save(customer);
        assertNotNull(customerRepository.findByEmail("cule.jordi@hotmail.com"));
    }

    @Test
    @DisplayName("Should find a customer by phone")
    void testFindByPhone() {
        customerRepository.save(customer);
        assertNotNull(customerRepository.findByPhone("600102030"));
    }

    @Test
    @DisplayName("Should find all customers from same city")
    void testFindByAddressCity() {
        customerRepository.save(customer);
        assertFalse(customerRepository.findAllByAddressCity("Barcelona").isEmpty());
    }

    @Test
    @DisplayName("Should find all customers from same postal code")
    void testFindByAddressPostalCode() {
        customerRepository.save(customer);
        assertFalse(customerRepository.findAllByAddressPostalCode("08001").isEmpty());
    }

}