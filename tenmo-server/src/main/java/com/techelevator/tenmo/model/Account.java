package com.techelevator.tenmo.model;

public class Account {

    private int accountId;
    private int userId;
    private double balance;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}



