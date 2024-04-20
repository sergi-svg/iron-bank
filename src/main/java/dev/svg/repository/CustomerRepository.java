package dev.svg.repository;

import dev.svg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByIdCard(String idCard);
    Customer findByEmail(String email);
    Customer findByPhone(String phone);
}
