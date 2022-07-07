package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

public interface TransferDao {

    // List of transfers


    // Create transfer

    boolean createTransfer(String receivingUsername, String sendingUsername, Double transferAmount);

    //Get transfer by id
}
