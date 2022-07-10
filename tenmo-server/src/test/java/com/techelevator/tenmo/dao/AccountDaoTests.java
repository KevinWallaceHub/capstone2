package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class AccountDaoTests extends  BaseDaoTests{

    private JdbcAccountDao sut;
    private Account testAccount = new Account();

    @Before
    public void setup() {
        sut = new JdbcAccountDao(new JdbcTemplate(dataSource));
        testAccount.setBalance(1000);
        testAccount.setUserId(1001);
        testAccount.setAccountId(2001);
    }

    @Test
    public void get_account_by_id_returns_account(){
        Account returnedAccount = sut.getAccount(1001);
        Assert.assertEquals(testAccount, returnedAccount);
    }

    @Test
    public void increase_adds_to_account_balance(){
        sut.increaseAccountBalance("testUserOne", 100.00);
        double accountBalance = sut.getAccount(1001).getBalance();
        Assert.assertEquals(1100.00, accountBalance, 0.009);
    }

    @Test
    public void decrease_subtracts_from_account_balance(){
        sut.decreaseAccountBalance("testUserOne", 100.00);
        double accountBalance = sut.getAccount(1001).getBalance();
        Assert.assertEquals(900.00, accountBalance, 0.009);
    }

}
