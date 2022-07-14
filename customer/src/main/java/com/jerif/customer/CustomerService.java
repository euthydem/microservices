package com.jerif.customer;

import com.jerif.clients.fraud.FraudCheckResponse;
import com.jerif.clients.fraud.FraudClient;
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
    private final FraudClient fraudClient;


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
        FraudCheckResponse fraudster = fraudClient.isFraudster(customer.getId());

        //todo: send notification

    }
}
