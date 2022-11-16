package com.banking_demo_project.banking_api.services;

import com.banking_demo_project.banking_api.models.entities.Account;
import com.banking_demo_project.banking_api.models.req.AccountReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public Account createNewAccount(AccountReq accountReq){
        Account newAccount = new Account(accountReq.getUserId(), accountReq.getBalance(), accountReq.getCurrency());
        accountRepository.insert(newAccount);
        return newAccount;
    }

    public Account getAccountById(String accountId){
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is(accountId));
        return mongoTemplate.find(query, Account.class).get(0);
    }

    public List<Account> getAllAccountsOfUserById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Account.class);
    }

    public void deleteAccount(String accountId){
        accountRepository.deleteById(accountId);
    }

    public void transferAmount(Account from, Account to, float amount){
        if(from.getBalance() >= amount){
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            mongoTemplate.save(from);
            mongoTemplate.save(to);
        }
        else{
            // TODO hesaptaki para daha az ise exception raise et
        }

    }
}
