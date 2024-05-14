package dev.svg.services;

import dev.svg.models.customer.Customer;
import dev.svg.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getAllCustomersByPostalCode(String postalCode) {
        return customerRepository.findAllByAddressPostalCode(postalCode);
    }

    public List<Customer> getAllCustomersByCity(String city) {
        return customerRepository.findAllByAddressCity(city);
    }

    public Optional<Customer> getCustomerByIdCard(String idCard) {
        return customerRepository.findByIdCard(idCard);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String idCard, Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findByIdCard(idCard);
        if (optionalCustomer.isPresent()) {
            customer.setIdCard(optionalCustomer.get().getIdCard());
            return customerRepository.save(customer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteByIdCard(String idCard) {
        Optional<Customer> optionalCustomer = customerRepository.findByIdCard(idCard);
        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(optionalCustomer.get().getIdCard());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }
}
