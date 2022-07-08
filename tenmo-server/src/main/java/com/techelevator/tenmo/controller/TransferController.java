package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.security.Principal;

@RestController
public class TransferController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    public TransferController(AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    @RequestMapping (path = "accounts/{id}/transfers", method = RequestMethod.POST)
    public boolean createTransfer(@PathVariable int id, @RequestBody TransferDTO transferDto){
        boolean success = false;
        try {
            transferDao.createTransfer(transferDto.getSendingUsername(), transferDto.getReceivingUsername(),
                    transferDto.getAmount());
            success = true;
        } catch (ResourceAccessException e){
            return success;
        }
        if( success == true){
            accountDao.decreaseAccountBalance(transferDto.getSendingUsername(), transferDto.getAmount());
            accountDao.increaseAccountBalance(transferDto.getReceivingUsername(),transferDto.getAmount());
      }

        return success;

    }
}