package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping (path = "accounts/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable int id){
    return accountDao.getAccount(id);
    }

    //fix here before the client side
    @RequestMapping(path = "accounts", method = RequestMethod.GET)
    public User[] findAllUsers(String username){
        return userDao.findAll(username);
    }

}
