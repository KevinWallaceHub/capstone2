package com.techelevator.tenmo.model;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private String receivingUsername;
    private String sendingUsername;
    private int accountFrom;
    private int accountTo;
    private Double amount;

    public String getReceivingUsername() {
        return receivingUsername;
    }

    public void setReceivingUsername(String receivingUsername) {
        this.receivingUsername = receivingUsername;
    }

    public String getSendingUsername() {
        return sendingUsername;
    }

    public void setSendingUsername(String sendingUsername) {
        this.sendingUsername = sendingUsername;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
