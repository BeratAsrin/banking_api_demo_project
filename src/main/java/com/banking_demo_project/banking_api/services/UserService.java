package com.banking_demo_project.banking_api.services;

import com.banking_demo_project.banking_api.entities.models.User;
import com.banking_demo_project.banking_api.entities.req.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public User createNewUser(UserReq userReq){
        User newUser = new User(userReq.getName(), userReq.getSurname(), userReq.getEmail());
        userRepository.insert(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.find(query, User.class).get(0);
    }

    public User updateData(UserReq userReq){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userReq.getUserId()));
        Update update = new Update()
                .set("name", userReq.getName())
                .set("surname", userReq.getSurname())
                .set("email", userReq.getEmail());
        mongoTemplate.updateFirst(query, update, "users");
        return getUserById(userReq.getUserId());
    }
}
