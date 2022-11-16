package com.banking_demo_project.banking_api.services;

import com.banking_demo_project.banking_api.models.entities.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
}
