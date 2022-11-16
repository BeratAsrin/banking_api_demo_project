package com.banking_demo_project.banking_api.controllers;

import com.banking_demo_project.banking_api.models.res.ErrorRes;
import com.banking_demo_project.banking_api.models.entities.Account;
import com.banking_demo_project.banking_api.models.entities.User;
import com.banking_demo_project.banking_api.models.req.AccountReq;
import com.banking_demo_project.banking_api.models.req.TransferReq;
import com.banking_demo_project.banking_api.models.req.UserReq;
import com.banking_demo_project.banking_api.models.res.SuccessRes;
import com.banking_demo_project.banking_api.models.res.Res;
import com.banking_demo_project.banking_api.services.AccountService;
import com.banking_demo_project.banking_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService = new AccountService();

    @PostMapping("/user/create")
    public Res createNewUser(@RequestBody UserReq userReq){
        if(userService.getUserByTCKN(userReq.getTckn()) != null){
            return new ErrorRes("There is already registered user with this tckn.");
        }
        User newUser = userService.createNewUser(userReq);
        if(newUser != null){
            return new SuccessRes(newUser, "New user is created.");
        }
        return new ErrorRes("User could not be created.");
    }

    @PutMapping("/user/update")
    public Res updateUser(@RequestBody UserReq userReq){
        User updatedUser = userService.updateData(userReq);
        if(updatedUser != null){
            if(userReq.getName().equals(updatedUser.getName())
                    && userReq.getSurname().equals(updatedUser.getSurname())
                    && userReq.getEmail().equals(updatedUser.getEmail())){
                if (userReq.getTckn() != null){
                    return new SuccessRes(updatedUser, "User is updated except tckn.");
                }
                return new SuccessRes(updatedUser, "User is updated.");
            }
        }
        return new ErrorRes("User could not be updated.");
    }

    @GetMapping("/user/get_all")
    public Res getAllUsers(){
        List<User> users = userService.getAllUsers();
        if(users != null){
            return new SuccessRes(users, users.size() + " users are returned.");
        }
        return new ErrorRes("DB connection error.");
    }

    @PostMapping("/account/create")
    public Res createNewAccount(@RequestBody AccountReq accountReq){
        if(accountReq.getUserId() == null && accountReq.getUserTckn() == null){
            return new ErrorRes("Please enter TCKN or userId.");
        }
        else if(accountReq.getUserId() == null){
            User user = userService.getUserByTCKN(accountReq.getUserTckn());
            accountReq.setUserId(user.getUserId());
        }
        else if(accountReq.getUserTckn() == null){
            User user = userService.getUserById(accountReq.getUserId());
            accountReq.setUserTckn(user.getTckn());
        }
        Account newAccount = accountService.createNewAccount(accountReq);
        if(newAccount != null){
            return new SuccessRes(newAccount, "New account is created.");
        }
        return new ErrorRes("Account could not be created.");
    }

    @GetMapping("/account/user/get_all")
    public Res getAllAccountsOfUser(@RequestBody AccountReq accountReq){
        List<Account> accounts = null;
        if(accountReq.getUserId() != null){
            accounts = accountService.getAllAccountsOfUserById(accountReq.getUserId());
        }
        else if(accountReq.getUserTckn() != null){
            accounts = accountService.getAllAccountsOfUserByTCKN(accountReq.getUserTckn());
        }
        if(accounts != null){
            return new SuccessRes(accounts, accounts.size() + " accounts are returned.");
        }
        return new ErrorRes("DB connection error.");
    }

    @DeleteMapping("/account/user/delete")
    public Res deleteAccountOfUser(@RequestBody AccountReq accountReq){
        if (accountService.deleteAccount(accountReq.getAccountId())){
            return new SuccessRes("", "Account with id " + accountReq.getAccountId() + " is deleted.");
        }
        return new ErrorRes("Account could not be deleted.");
    }

    @PostMapping("/account/transfer")
    @ResponseBody
    public Object transferBalance(@RequestBody TransferReq transferReq){
        Account fromAccount = accountService.getAccountById(transferReq.getFromAccountId());
        Account toAccount = accountService.getAccountById(transferReq.getToAccountId());

        if(fromAccount.getCurrency().equals(toAccount.getCurrency())){
            if(fromAccount.getBalance() >= transferReq.getAmount()){
                accountService.transferAmount(fromAccount, toAccount, transferReq.getAmount());
                ArrayList<Account> accounts = new ArrayList<>();
                accounts.add(accountService.getAccountById(transferReq.getFromAccountId()));
                accounts.add(accountService.getAccountById(transferReq.getToAccountId()));
                if(accounts.get(0).getUserTckn().equals(accounts.get(1).getUserTckn())){
                    return new SuccessRes(accounts,
                            transferReq.getAmount() + " in " + accounts.get(0).getCurrency() + " is transferred from account " +
                                    accounts.get(0).getAccountId() + " to account " + accounts.get(1).getAccountId() +
                                    ". These accounts are owned by (TCKN)" + accounts.get(0).getUserTckn() + ".");
                }
                else{
                    return new SuccessRes(accounts,
                            transferReq.getAmount() + " in " + accounts.get(0).getCurrency() + " is transferred from account " +
                                    accounts.get(0).getAccountId() + " to account " + accounts.get(1).getAccountId() +
                                    ". These accounts are owned by (TCKN)" + accounts.get(0).getUserTckn() + " and (TCKN)" +
                                    accounts.get(1).getUserTckn() + " respectively.");
                }
            }
            else{
                return new ErrorRes("Balance of sending (fromAccount) account is less than transaction amount.");
            }

        }

        return new ErrorRes("These accounts do not have the same currency.");

    }

}
