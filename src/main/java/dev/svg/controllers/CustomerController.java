package dev.svg.controllers;

import dev.svg.model.Customer;
import dev.svg.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/iron-bank")
public class CustomerController implements ResourceController <Customer> {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllResources() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/customer")
    @ResponseStatus(HttpStatus.OK)
    public Customer getResourceByParams(@RequestParam HashMap<String, String> params) {
        Customer customer = null;
        if (params.containsKey("idCard")) customer = customerService.getCustomerByIdCard(params.get("idCard"));
        else if (params.containsKey("email")) customer = customerService.getCustomerByEmail(params.get("email"));
        else if (params.containsKey("phone")) customer = customerService.getCustomerByPhone(params.get("phone"));

        return customer;
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResource(@PathVariable("id") Long id) {
        customerService.deleteById(id);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createResource(@RequestBody @Valid Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/customers")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Customer updateResource(Customer customer) {
        return customerService.updateCustomer(customer);
    }


}
