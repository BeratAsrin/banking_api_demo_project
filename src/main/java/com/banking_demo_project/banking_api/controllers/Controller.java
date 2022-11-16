package com.banking_demo_project.banking_api.controllers;

import com.banking_demo_project.banking_api.entities.models.User;
import com.banking_demo_project.banking_api.entities.req.UserReq;
import com.banking_demo_project.banking_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserService userService;

    @PostMapping("/user/create")
    public User createNewUser(@RequestBody UserReq userReq){
        return userService.createNewUser(userReq);
    }

    @PutMapping("/user/update")
    public User updateUser(@RequestBody UserReq userReq){
        return userService.updateData(userReq);
    }

    @GetMapping("/user/get_all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/account/create")
    public void createNewAccount(){
    }

    @GetMapping("/account/user/get_all")
    public void getAllAccountsOfUser(){
    }

    @DeleteMapping("/account/user/delete")
    public void deleteAccountOfUser(){
    }

    @PostMapping("/account/transfer")
    public void transferBalance(){
    }

}
