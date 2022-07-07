package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    public TransferService(String baseUrl){
        this.baseUrl = baseUrl;}

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean createTransfer(long userId, Transfer transfer) {


        HttpEntity<Transfer> entity = makeTransferAuthEntity(transfer);
        boolean success = false;
        try {
            String url = baseUrl + "accounts/" + userId + "/transfers";
            restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
            success = true;
        }catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    private HttpEntity<Transfer> makeTransferAuthEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<Transfer>(transfer, headers);
    }
}
