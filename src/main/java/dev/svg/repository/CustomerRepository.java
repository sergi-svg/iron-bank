package dev.svg.repository;

import dev.svg.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByIdCard(String idCard);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);

}
