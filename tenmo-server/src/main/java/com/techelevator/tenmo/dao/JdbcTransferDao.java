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
        String sql = "SELECT t.Transfer_Id, t.Transfer_Type_Id, " +
                "t.Transfer_Status_Id, UAL1.Username AS sending_UserName, " +
                "t.account_to, t.account_from, " +
                "UAL2.Username AS receiving_Username, " +
                "t.Amount FROM tenmo_user tu " +
                "INNER JOIN " +
                "account a ON tu.user_id = a.user_id " +
                "INNER JOIN " +
                "transfer t ON a.account_id = t.account_from " +
                "OR a.Account_id = t.account_to " +
                "INNER JOIN(SELECT TU.username, A.Account_Id " +
                "FROM tenmo_user TU " +
                "INNER JOIN " +
                "account A " +
                "ON TU.user_id = A.user_id) UAL1 " +
                "ON T.Account_From = UAL1.Account_ID " +
                "INNER JOIN(SELECT TU.username, A.Account_Id " +
                "FROM tenmo_user TU " +
                "INNER JOIN account A ON TU.user_id = A.user_id) UAL2 " +
                "ON T.Account_To = UAL2.Account_ID " +
                "WHERE TU.username= ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        while (results.next()){
            transfers.add(mapRowToTransfer(results));
        }

        return transfers;
    }

    @Override
    public boolean createTransfer(int transferTypeId, int transferStatusId, String sendingUsername, String receivingUsername, Double transferAmount) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?,?, (SELECT a.account_id FROM account a JOIN tenmo_user t ON t.user_id = a.user_id WHERE t.username = ?), " +
                "(SELECT a.account_id FROM account a JOIN tenmo_user t ON t.user_id = a.user_id WHERE t.username = ?), ?)";
        try {
            jdbcTemplate.update(sql, transferTypeId, transferStatusId, sendingUsername, receivingUsername, transferAmount);
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
        transfer.setReceivingUsername(result.getString("receiving_username"));
        transfer.setSendingUsername(result.getString("sending_username"));
        transfer.setAccountFrom(result.getInt("account_from"));
        transfer.setAccountTo(result.getInt("account_to"));
        transfer.setAmount(result.getDouble("amount"));
        return transfer;
    }

}
