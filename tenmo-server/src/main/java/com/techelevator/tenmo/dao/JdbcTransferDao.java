package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> listTransfers(String username) {
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN account a ON a.account_id = t.account_from OR a.account_id = t.account_to " +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id " +
                "WHERE tu.username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        while (results.next()){
            transfers.add(mapRowToTransfer(results));
        }

        return transfers;
    }

    @Override
    public boolean createTransfer(String sendingUsername, String receivingUsername, Double transferAmount) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (2,2, (SELECT a.account_id FROM account a JOIN tenmo_user t ON t.user_id = a.user_id WHERE t.username = ?), " +
                "(SELECT a.account_id FROM account a JOIN tenmo_user t ON t.user_id = a.user_id WHERE t.username = ?), ?)";
        try {
            jdbcTemplate.update(sql, sendingUsername, receivingUsername, transferAmount);
        } catch (DataAccessException e){
            return false;
        }
            return true;
    }



    private Transfer mapRowToTransfer(SqlRowSet result) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setTransferTypeId(result.getInt("transfer_type_id"));
        transfer.setTransferStatusId(result.getInt("transfer_status_id"));
        transfer.setAccountFrom(result.getInt("account_from"));
        transfer.setAccountTo(result.getInt("account_to"));
        transfer.setAmount(result.getDouble("amount"));
        return transfer;
    }

}
