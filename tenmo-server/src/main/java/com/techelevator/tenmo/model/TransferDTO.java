package com.techelevator.tenmo.model;

public class TransferDTO {

    private int transferTypeId;
    private int transferStatusId;
    private String sendingUsername;
    private String receivingUsername;
    private Double amount;


    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int typeId) {
        this.transferTypeId = typeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }


    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

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


    @Override
    public String toString() {
        return "TransferDTO{" +
                "sendingUsername='" + sendingUsername + '\'' +
                ", receivingUsername='" + receivingUsername + '\'' +
                ", amount=" + amount +
                ", transferTypeId=" + transferTypeId +
                ", transferStatusId=" + transferStatusId +
                '}';
    }
}
