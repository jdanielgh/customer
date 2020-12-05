package co.com.poli.shop.customer.services;

import co.com.poli.shop.customer.domain.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer deleteCustomer(Customer customer);
    Customer getCustomerByNumberId(String id);
    Customer getCustomer(Long id);
}
