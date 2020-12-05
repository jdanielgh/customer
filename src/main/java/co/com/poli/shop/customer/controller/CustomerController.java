package co.com.poli.shop.customer.controller;

import co.com.poli.shop.customer.domain.Customer;
import co.com.poli.shop.customer.model.ErrorMessage;
import co.com.poli.shop.customer.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        log.info("Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            log.info("Costumer with Id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping(value = "/idetification/{idNumber}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("idNumber") String id) {
        log.info("Customer with id {}", id);
        Customer customer = customerService.getCustomerByNumberId(id);
        if (customer == null) {
            log.info("Costumer with numberID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        log.info("Create customer {}", customer);
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Customer customerBD = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerBD);
    }

    @PutMapping(value = "/{numberId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("numberid") String numberId, @RequestBody Customer customer) {
     log.info("Updating customer with numberId {}", numberId);
     Customer customerBD = customerService.updateCustomer(customer);

     if (customerBD == null) {
         log.error("Unabled to update Customer with numberId {} not found", numberId);
         return ResponseEntity.notFound().build();
     }
     customer.setNumberID(numberId);
     customerBD = customerService.updateCustomer(customer);
     return ResponseEntity.ok(customerBD);
    }

    @PutMapping(value = "/{numberid}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("numberid") String numberId) {
        log.info("Updating customer with numberId {}", numberId);
        Customer customerBD = customerService.getCustomerByNumberId(numberId);

        if (customerBD == null) {
            log.error("Unabled to update Customer with numberId {} not found", numberId);
            return ResponseEntity.notFound().build();
        }
        customerBD = customerService.deleteCustomer(customerBD);
        return ResponseEntity.ok(customerBD);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> messages = result.getFieldErrors().stream()
                .map( err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(messages).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
