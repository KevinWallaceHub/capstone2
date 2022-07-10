package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {

    // List of transfers

    List<Transfer> listTransfers(String username);

    // Create transfer

    boolean createTransfer(int typeId, int StatusId, String receivingUsername, String sendingUsername, Double transferAmount);


}
