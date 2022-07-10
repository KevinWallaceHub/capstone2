package com.techelevator.tenmo.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && userId == account.userId && Double.compare(account.balance, balance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userId, balance);
    }

}



