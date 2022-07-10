package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.Arrays;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final ConsoleService consoleService = new ConsoleService();
    private final TransferService transferService = new TransferService(API_BASE_URL);
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        //no users until this block runs
        if (currentUser != null) {
            accountService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }
// Methods to implement the api Menu is currently displaying without functionality
	private void viewCurrentBalance() {
    double accountBalance = accountService.getAccountBalance(currentUser.getUser().getId());
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String formattedBalance = formatter.format(accountBalance);
        System.out.println("Current account balance: " + formattedBalance);
	}

	private void viewTransferHistory() {
		Transfer[] transferArray = transferService.listTransfers(currentUser.getUser().getId(), currentUser.getUser().getUsername());
        consoleService.printListOfTransfers(transferArray, currentUser.getUser());
        Transfer selectedTransfer = null;
        selectedTransfer = consoleService.transferDetailsFromUserSelection(transferArray);
        if (selectedTransfer != null){
        consoleService.printTransferDetails(selectedTransfer);}
	}

	private void viewPendingRequests() {
        Transfer[] transferArray = transferService.listTransfers(currentUser.getUser().getId(), currentUser.getUser().getUsername());
        consoleService.printListOfPendingTransfers(transferArray, currentUser.getUser());
        Transfer selectedTransfer = null;
        selectedTransfer = consoleService.transferDetailsFromUserSelection(transferArray);
        if (selectedTransfer != null){
            consoleService.printTransferDetails(selectedTransfer);}
	}

	private void sendBucks() {

        User[] userArray = accountService.findAllUsers(currentUser.getUser().getUsername());
        consoleService.printListOfUsersToSendMoney(userArray);
        User selectedUser = consoleService.userSelectionForTransfer(userArray);
        if (selectedUser != null){
        double accountBalance = accountService.getAccountBalance(currentUser.getUser().getId());
        double transferAmount = consoleService.getTransferAmount(accountBalance);
        Transfer transfer = new Transfer(currentUser.getUser().getUsername(), selectedUser.getUsername(), transferAmount);
        transferService.createTransfer(currentUser.getUser().getId(), transfer);}

	}

	private void requestBucks() {
        User[] userArray = accountService.findAllUsers(currentUser.getUser().getUsername());
        consoleService.printListOfUsersToSendMoney(userArray);
        User selectedUser = consoleService.userSelectionForTransfer(userArray);
        if (selectedUser != null){
            double requestAmount = consoleService.getRequestAmount();
            Transfer transfer = new Transfer(selectedUser.getUsername(), currentUser.getUser().getUsername(), requestAmount);
            transferService.createRequest(selectedUser.getId(), transfer);}
	}

}
