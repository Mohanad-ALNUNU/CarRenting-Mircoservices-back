package com.tuto.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String customerName;
    private String imageURL;
    private String password;
    private int birthDay;
    private PaymentMethod paymentMethod;

    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        PAYPAL,
        APPLE_PAY,
        CASH
    }

    //TODO::create private propriety (instance) of payment method from microservice payments

}
