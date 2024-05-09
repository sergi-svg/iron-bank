package dev.svg.repository;

import dev.svg.models.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    List<Customer> findAllByAddressPostalCode(String postalCode);
    List<Customer> findAllByAddressCity(String city);

    Optional<Customer> findByIdCard(String idCard);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
}
