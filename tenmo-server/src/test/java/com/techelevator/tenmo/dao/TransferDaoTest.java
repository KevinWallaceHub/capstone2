package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TransferDaoTest extends BaseDaoTests{

    private JdbcTransferDao sut;

    @Before
    public void setup() {
        sut = new JdbcTransferDao(new JdbcTemplate(dataSource));
    }

    @Test
    public void list_of_transfers_returns_all_transfers(){
    List<Transfer> testTransfers = sut.listTransfers("testUserOne");
        Assert.assertEquals(3,testTransfers.size());
    }

    @Test
    public void create_transfer_returns_true(){
        boolean wasTransferCreated = sut.createTransfer(2,2,"testUserOne", "testUserTwo", 100.00);
        Assert.assertTrue(wasTransferCreated);
    }

}
