package dev.svg.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.svg.models.account.CheckingAccount;
import dev.svg.models.account.SavingAccount;
import dev.svg.models.customer.Address;
import dev.svg.models.customer.Customer;
import dev.svg.models.customer.Name;
import dev.svg.services.AccountService;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    static Address address;
    static Address secondaryAddress;
    static Name name;
    static Customer customer;
    static List<Customer> customers = new ArrayList<>();

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
                "12345678A",
                name,
                address,
                secondaryAddress,
                "cule.jordi@hotmail.com",
                "600102030"
        );
        customerService.createCustomer(customer);
        customers.add(customer);

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200051332");
        checkingAccount.setBalance(10500);
        checkingAccount.setInterestRate(1.5);

        checkingAccount.setCustomers(customers);
        accountService.createAccount(checkingAccount);

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("ES9121000418450200051333");
        savingAccount.setBalance(20500);
        savingAccount.setInterestRate(3.5);

        savingAccount.setCustomers(customers);
        accountService.createAccount(savingAccount);
    }

    @AfterEach
    void tearDown() {
        accountService.deleteAllAccount();
        customerService.deleteAllCustomers();
    }

    @Test
    void getAllResources() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("ES9121000418450200051332"));
    }

    @Test
    void testGetResourceByParams_ReturnsAccountByAccountNumber() throws Exception {
        String accountNumber = "ES9121000418450200051332";
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/account")
                        .param("accountNumber", accountNumber))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("10500"));
    }

    @Test
    void testGetResourcesByParams_ReturnsAccountsByCity() throws Exception {
        final String cityParam = "Barcelona";
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/accounts_by")
                        .param("city", cityParam))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains(cityParam));
    }

    @Test
    void testGetResourcesByParams_ReturnsAccountsByPostalCode() throws Exception {
        String postalCodeParam = "08001";
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/accounts_by")
                        .param("postalCode", postalCodeParam))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("12345678A"));
    }

    @Test
    void testGetCheckingAccount_ReturnsAllCheckingAccounts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/accounts/checking"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("10500"));
    }

    @Test
    void testGetSavingAccount_ReturnsAllSavingAccounts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/iron-bank/accounts/saving"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("20500"));
    }

    @Test
    void createResource() throws Exception {
        name = new Name();
        name.setName("Jordi2");
        name.setSurname("Culé");

        customer = new Customer(
                "12345678B",
                "12345678B",
                name,
                address,
                secondaryAddress,
                "cule.jordi2@hotmail.com",
                "600101010"
        );
        customerService.createCustomer(customer);

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountNumber("ES9121000418450200054321");
        checkingAccount.setBalance(500);
        checkingAccount.setInterestRate(1.5);

        checkingAccount.setCustomers(customers);
        //accountService.createAccount(checkingAccount);

        String body = objectMapper.writeValueAsString(checkingAccount);

        MvcResult mvcResult = mockMvc.perform(post("/iron-bank/accounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("ES9121000418450200054321"));
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
                "12345678A",
                name,
                address,
                secondaryAddress,
                "cule.jordi@gmail.com",
                "600102030"
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