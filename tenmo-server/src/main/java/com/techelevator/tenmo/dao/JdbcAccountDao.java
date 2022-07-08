package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(long userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account " +
                "WHERE user_id =?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while (result.next()){
            account = mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public double increaseAccountBalance(String receivingUsername, double amount) {
        Double newBalance = null;
        String sql = "UPDATE account " +
                "SET balance = (balance + ?) " +
                "FROM tenmo_user " +
                "WHERE tenmo_user.user_id = account.user_id AND tenmo_user.username = ? " +
                "RETURNING balance";
         newBalance = Double.valueOf(jdbcTemplate.update(sql, receivingUsername, amount));

        return newBalance;
    }

    @Override
    public double decreaseAccountBalance(String sendingUsername, double amount) {
        Double newBalance = null;
        String sql = "UPDATE account " +
                "SET balance = (balance - ?) " +
                "FROM tenmo_user " +
                "WHERE tenmo_user.user_id = account.user_id AND tenmo_user.username = ? " +
                "RETURNING balance";
        newBalance = Double.valueOf(jdbcTemplate.update(sql, sendingUsername, amount));

        return newBalance;

    }


//    @Override
//    public List<Account> listAccounts(long userId) {
//        List<Account> accounts = new ArrayList<Account>();
//        String sql = "SELECT account_id, user_id FROM account " +
//                "WHERE user_id != ?";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
//        while (result.next()){
//            accounts.add(mapRowToAccount(result));
//        }
//        return accounts;
//    }

    private Account mapRowToAccount(SqlRowSet result){
    Account account = new Account();
    account.setAccountId(result.getInt("account_id"));
    account.setUserId(result.getInt("user_id"));
    account.setBalance(result.getDouble("balance"));
    return account;
    }
}
