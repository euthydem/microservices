package com.jerif.customer;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        //todo: check if email is valid
        //todo: check if email not taken
        //todo: check if is Fraudster

        customerRepository.saveAndFlush(customer);
        FraudCheckResponse response = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());

        if (response.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        //todo: send notification

    }
}
