package dev.svg.services;

import dev.svg.model.Customer;
import dev.svg.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByIdCard(String idCard) {
        return customerRepository.findByIdCard(idCard);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

}
