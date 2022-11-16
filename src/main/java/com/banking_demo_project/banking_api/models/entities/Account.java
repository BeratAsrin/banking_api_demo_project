package com.banking_demo_project.banking_api.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("accounts")
public class Account {
    @Id
    private String accountId;
    private String userId;

    private float balance;
    private String currency;

    public Account(String userId, float balance, String currency) {
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }
}
