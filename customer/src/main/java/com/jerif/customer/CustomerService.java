package com.jerif.customer;

import com.jerif.clients.fraud.FraudCheckResponse;
import com.jerif.clients.fraud.FraudClient;
import com.jerif.clients.notification.NotificationClient;
import com.jerif.clients.notification.NotificationRequest;
import com.jerif.amqp.RabbitMQMessageProducer;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private final NotificationClient notificationClient;
    private final FraudClient fraudClient;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

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

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to JerifServices...",
                        customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(notificationRequest,"internal.exchange",
                "internal.notification.routing-key");


    }
}
