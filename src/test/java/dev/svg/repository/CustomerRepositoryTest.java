package dev.svg.repository;

import dev.svg.model.Address;
import dev.svg.model.Customer;
import dev.svg.model.Name;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
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

        customer = new Customer(1L,
                "12345678A",
                name,
                address,
                secondaryAddress,
                "cule.jordi@hotmail.com",
                "600102030"
        );
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new user")
    void testCreateNewUser() {
        customerRepository.save(customer);
        assertNotNull(customerRepository.findById(1L));
    }

    @Test
    @DisplayName("Should find a user by email")
    void testFindByEmail() {
        customerRepository.save(customer);
        assertNotNull(customerRepository.findByEmail("cule.jordi@hotmail.com"));
    }

    @Test
    @DisplayName("Should find a user by phone")
    void testFindByPhone() {
        customerRepository.save(customer);
        assertNotNull(customerRepository.findByPhone("600102030"));
    }

}