package com.banking_demo_project.banking_api.services;

import com.banking_demo_project.banking_api.models.entities.User;
import com.banking_demo_project.banking_api.models.req.UserReq;
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
        User newUser = new User(userReq.getTckn(), userReq.getName(), userReq.getSurname(), userReq.getEmail());
        try{
            userRepository.insert(newUser);
            return newUser;
        } catch (Exception e){
            return null;
        }

    }

    public List<User> getAllUsers(){
        try{
            return userRepository.findAll();
        } catch (Exception e){
            return null;
        }
    }

    public User getUserById(String id){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            return mongoTemplate.find(query, User.class).get(0);
        } catch (Exception e){
            return null;
        }
    }

    public User getUserByTCKN(String tckn){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("tckn").is(tckn));
            return mongoTemplate.find(query, User.class).get(0);
        } catch (Exception e){
            return null;
        }
    }

    public User updateData(UserReq userReq){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(userReq.getUserId()));
            Update update = new Update()
                    .set("name", userReq.getName())
                    .set("surname", userReq.getSurname())
                    .set("email", userReq.getEmail());
            mongoTemplate.updateFirst(query, update, "users");
            return getUserById(userReq.getUserId());
        } catch (Exception e){
            return null;
        }

    }
}
