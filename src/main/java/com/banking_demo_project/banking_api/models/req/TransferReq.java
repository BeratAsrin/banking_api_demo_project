package com.banking_demo_project.banking_api.models.req;

import lombok.Data;

@Data
public class TransferReq {
    private String fromAccountId;
    private String toAccountId;
    private float amount;
}
