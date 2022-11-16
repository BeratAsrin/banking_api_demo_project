package com.banking_demo_project.banking_api.controllers;

import com.banking_demo_project.banking_api.entities.models.Account;
import com.banking_demo_project.banking_api.entities.models.User;
import com.banking_demo_project.banking_api.entities.req.AccountReq;
import com.banking_demo_project.banking_api.entities.req.TransferReq;
import com.banking_demo_project.banking_api.entities.req.UserReq;
import com.banking_demo_project.banking_api.services.AccountService;
import com.banking_demo_project.banking_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService = new AccountService();

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
    public Account createNewAccount(@RequestBody AccountReq accountReq){
        return accountService.createNewAccount(accountReq);
    }

    @GetMapping("/account/user/get_all")
    public List<Account> getAllAccountsOfUser(@RequestBody AccountReq accountReq){
        return accountService.getAllAccountsOfUserById(accountReq.getUserId());
    }

    @DeleteMapping("/account/user/delete")
    public void deleteAccountOfUser(@RequestBody AccountReq accountReq){
        accountService.deleteAccount(accountReq.getAccountId());
    }

    @PostMapping("/account/transfer")
    public Object transferBalance(@RequestBody TransferReq transferReq){
        Account fromAccount = accountService.getAccountById(transferReq.getFromAccountId());
        Account toAccount = accountService.getAccountById(transferReq.getToAccountId());
        if(fromAccount.getCurrency().equals(toAccount.getCurrency())){
            accountService.transferAmount(fromAccount, toAccount, transferReq.getAmount());
            return "OK";
        }
        return "These accounts do not have same currency."; // TODO JSON ERRORU HALINE GETIR

    }

}
