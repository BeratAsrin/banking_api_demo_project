package com.banking_demo_project.banking_api.models.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessRes implements Res {
    private Object answer;
    private String message;
}
