package dev.svg.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.svg.model.customer.Address;
import dev.svg.model.customer.Customer;
import dev.svg.model.customer.Name;
import dev.svg.repository.CustomerRepository;
import dev.svg.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    CustomerService customerService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    static Address address;
    static Address secondaryAddress;
    static Name name;
    static Customer customer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
        customerService.createCustomer(customer);
    }

    @AfterEach
    void tearDown() {
        customerService.deleteAllCustomers();
    }

    @Test
    void getAllResources() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/customers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("12345678A"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("600102030"));
    }

    @Test
    void getResourceByParams_ReturnsCustomerByIdCard() throws Exception {
        String idCardParam = "12345678A";
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/customer")
                        .param("idCard", idCardParam))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("cule.jordi@hotmail.com"));
    }

    @Test
    void getResourceByParams_ReturnsCustomerByEmail() throws Exception {
        String emailParam = "cule.jordi@hotmail.com";
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/customer")
                        .param("email", emailParam))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("12345678A"));
    }

    @Test
    void getResourceByParams_ReturnsCustomerByPhone() throws Exception {
        String phoneNumberParam = "600102030";
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/customer")
                        .param("phone", phoneNumberParam))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("12345678A"));
    }

    @Test
    void createResource() throws Exception {
        address = new Address();
        address.setCity("Barcelona");
        address.setPostalCode("08020");
        address.setStreet("Gran via c.c");
        address.setStreetNumber("111");

        secondaryAddress = new Address();
        secondaryAddress.setCity("Tossa de Mar");
        secondaryAddress.setPostalCode("17320");
        secondaryAddress.setStreet("Carrer del mar");
        secondaryAddress.setStreetNumber("5");

        name = new Name();
        name.setName("Leo");
        name.setSurname("Messi");

        customer = new Customer(
                "12345678M",
                name,
                address,
                secondaryAddress,
                "messi.leo@hotmail.com",
                "600101010", null
        );

        String body = objectMapper.writeValueAsString(customer);
        MvcResult mvcResult = mockMvc.perform(post("/iron-bank/customers")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("messi.leo@hotmail.com"));
    }

    @Test
    void updateResource() throws Exception {
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
                "cule.jordi@gmail.com",
                "600102030", null
        );

        String body = objectMapper.writeValueAsString(customer);
        MvcResult mvcResult = mockMvc.perform(put("/iron-bank/customers/12345678A")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("cule.jordi@gmail.com"));
    }

    @Test
    void deleteResource() throws Exception {
        mockMvc.perform(delete("/iron-bank/customers/12345678A"))
                .andExpect(status().isOk()).andReturn();

        Optional<Customer> customers = customerService.getCustomerByIdCard("12345678A");
        assertFalse(customers.isPresent());
    }

    @Test
    void deleteAllResource() throws Exception {
        mockMvc.perform(delete("/iron-bank/customers"))
                .andExpect(status().isOk()).andReturn();

        List<Customer> customers = customerService.getAllCustomers();
        assertTrue(customers.isEmpty());
    }
}