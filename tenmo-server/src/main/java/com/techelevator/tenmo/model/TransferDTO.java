package com.techelevator.tenmo.model;

public class TransferDTO {

    private String sendingUsername;
    private String receivingUsername;
    private Double amount;

    public String getSendingUsername() {
        return sendingUsername;
    }

    public void setSendingUsername(String sendingUsername) {
        this.sendingUsername = sendingUsername;
    }

    public String getReceivingUsername() {
        return receivingUsername;
    }

    public void setReceivingUsername(String receivingUsername) {
        this.receivingUsername = receivingUsername;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
