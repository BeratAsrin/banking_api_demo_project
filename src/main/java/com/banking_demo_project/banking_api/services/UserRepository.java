package com.banking_demo_project.banking_api.services;

import com.banking_demo_project.banking_api.models.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
