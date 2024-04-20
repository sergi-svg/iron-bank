package dev.svg.controllers;

import dev.svg.model.Customer;
import dev.svg.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iron-bank")
public class CustomerController implements ResourceController <Customer> {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers/{param}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getResourceByParam(@PathVariable String param) {
        return switch (param.split("=")[0]) {
            case "idCard" -> customerService.getCustomerByIdCard(param.split("=")[1]);
            case "email" -> customerService.getCustomerByEmail(param);
            case "phone" -> customerService.getCustomerByPhone(param);
            default -> null;
        };
    }

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllResources() {
        return customerService.getAllCustomers();
    }

    @Override
    public Customer updateResource(Customer customer) {
        return null;
    }

    @Override
    public Customer createResource(Customer customer) {
        return null;
    }
}
