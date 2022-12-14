package com.banking_demo_project.banking_api.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class User {
    @Id
    private String userId;

    private String tckn;
    private String name;
    private String surname;
    private String email;

    public User(String tckn, String name, String surname, String email) {
        this.tckn = tckn;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
