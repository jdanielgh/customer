package co.com.poli.shop.customer.repository;

import co.com.poli.shop.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByNumberID(String numberId);
}
