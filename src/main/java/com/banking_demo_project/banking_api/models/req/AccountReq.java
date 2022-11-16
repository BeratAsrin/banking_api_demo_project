package com.banking_demo_project.banking_api.models.req;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class AccountReq{
    @Id
    private String accountId;

    private String userId;
    private String userTckn;
    private float balance;
    private String currency;
}
