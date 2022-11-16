package com.banking_demo_project.banking_api.services;

import com.banking_demo_project.banking_api.models.entities.Account;
import com.banking_demo_project.banking_api.models.entities.User;
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
        try{
            Account newAccount = new Account(accountReq.getUserTckn(), accountReq.getUserId(), accountReq.getBalance(), accountReq.getCurrency());
            accountRepository.insert(newAccount);
            return newAccount;
        } catch (Exception e){
            return null;
        }

    }

    public Account getAccountById(String accountId){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("accountId").is(accountId));
            return mongoTemplate.find(query, Account.class).get(0);
        } catch (Exception e){
            return null;
        }
    }

    public List<Account> getAllAccountsOfUserById(String userId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(userId));
            return mongoTemplate.find(query, Account.class);
        } catch (Exception e){
            return null;
        }
    }

    public List<Account> getAllAccountsOfUserByTCKN(String tckn) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("userTckn").is(tckn));
            return mongoTemplate.find(query, Account.class);
        } catch (Exception e){
            return null;
        }
    }

    public boolean deleteAccount(String accountId){
        try {
            accountRepository.deleteById(accountId);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void transferAmount(Account from, Account to, float amount){
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        mongoTemplate.save(from);
        mongoTemplate.save(to);
    }
}
