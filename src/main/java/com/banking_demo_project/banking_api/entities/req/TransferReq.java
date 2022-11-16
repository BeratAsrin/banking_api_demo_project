package com.banking_demo_project.banking_api.entities.req;

import com.banking_demo_project.banking_api.entities.models.Account;
import lombok.Data;

@Data
public class TransferReq {
    private String fromAccountId;
    private String toAccountId;
    private float amount;
}
