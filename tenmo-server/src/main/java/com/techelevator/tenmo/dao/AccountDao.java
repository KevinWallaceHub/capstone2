package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    Account getAccount(long userId);

    double increaseAccountBalance(String receivingUsername, double amount);

    double decreaseAccountBalance(String sendingUsername, double amount);

}
