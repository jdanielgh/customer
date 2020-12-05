package co.com.poli.shop.customer.services;

import co.com.poli.shop.customer.domain.Customer;
import co.com.poli.shop.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerBD = customerRepository.findByNumberID(customer.getNumberID());
        if( customerBD != null) {
            return null;
        }
        customer.setState("Create");
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerBD = customerRepository.findByNumberID(customer.getNumberID());
        if( customerBD == null) {
            return null;
        }
        customerBD.setFirstName(customer.getFirstName());
        customerBD.setLastName(customer.getLastName());
        customerBD.setEmail(customer.getEmail());
        customerBD.setState("Updated");

        return customerRepository.save(customer);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerBD = customerRepository.findByNumberID(customer.getNumberID());
        if( customerBD == null) {
            return null;
        }
        customer.setState("Deleted");
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByNumberId(String id) {
        return customerRepository.findByNumberID(id);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
