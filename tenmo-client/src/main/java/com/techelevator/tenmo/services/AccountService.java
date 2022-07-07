package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

public class AccountService {


    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken;

    public AccountService(String url) {
        this.baseUrl = url;

    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public double getAccountBalance(long userId) throws NullPointerException{
    Account account = null;
    try {
        account = restTemplate.exchange(baseUrl + "accounts/" + userId,
                HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
    } catch (RestClientResponseException | ResourceAccessException e){
        BasicLogger.log(e.getMessage());
    }

        return account.getBalance();
    }

    public User[] findAllUsers(String userName) throws NullPointerException{
       // User[] users = null;
        try {
            User[] users = restTemplate.exchange(baseUrl + "accounts",
                    HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
            return users;
        } catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return null;
    }



    private HttpEntity<Account> makeAccountEntity(Account account){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<Account>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<Void>( headers);
    }




}
