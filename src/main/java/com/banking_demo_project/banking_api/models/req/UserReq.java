package com.banking_demo_project.banking_api.models.req;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserReq {
    @Id
    private String userId;

    private String name;
    private String surname;
    private String email;
}
