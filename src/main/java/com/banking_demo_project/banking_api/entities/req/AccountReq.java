package com.banking_demo_project.banking_api.entities.req;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class AccountReq{
    @Id
    private String accountId;

    private String userId;
    private float balance;
    private String currency;
}
