package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    Account getAccount(long userId);

    void increaseAccountBalance(String receivingUsername, double amount);

    void decreaseAccountBalance(String sendingUsername, double amount);

}
