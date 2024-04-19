package dev.svg.repository;

import dev.svg.model.Address;
import dev.svg.model.Customer;
import dev.svg.model.Name;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    static Address address;
    static Name name;

    @BeforeAll
    static void setUp() {
        address = new Address();
        address.setCity("Barcelona");
        address.setPostalCode("08001");
        address.setStreet("Gran via c.c");
        address.setStreetNumber("123");

        name = new Name();
        name.setName("John");
        name.setSurname("Doe");
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new user")
    void testCreateNewUser() {
        Customer customer = new Customer(1L,
                "12345678A",
                name,
                address,
                null,
                "doe.john@hotmail.com",
                "600102030"
        );

        customerRepository.save(customer);
        assertNotNull(customerRepository.findById(1L));
    }

    @Test
    @DisplayName("Should find a user by email")
    void testFindByEmail() {
        Customer customer = new Customer(1L,
                "12345678A",
                name,
                address,
                null,
                "doe.john@hotmail.com",
                "600102030"
        );

        customerRepository.save(customer);
        assertNotNull(customerRepository.findByEmail("doe.john@hotmail.com"));
    }

}