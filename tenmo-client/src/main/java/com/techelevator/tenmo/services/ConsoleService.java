package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printListOfUsersToSendMoney(User[] users) {
        System.out.println("---------------------------");
        System.out.println("Users");
        System.out.printf("%-22s%-22s\n", "ID", "Name");
        System.out.println("---------------------------");
        for (User user : users) {
            System.out.printf("%-22s%-22s\n", user.getId(), user.getUsername());
        }
        System.out.println("---------------------------");
    }

    public User userSelectionForTransfer(User[] users) {
        User selectedUser = null;
        int userInput = -1;
        boolean validSelectionMade = false;
        while (validSelectionMade == false) {
            userInput = getUserInputForTransfer(userInput);
            if (userInput == 0) {
                validSelectionMade = true;
            }
            for (User user : users) {
                if (userInput == Math.toIntExact(user.getId())) {
                    validSelectionMade = true;
                    selectedUser = user;
                }
            }

            if (validSelectionMade == false){
                System.out.println("Please enter a valid user id number or 0 to exit");
            }

        }
        return selectedUser;
    }

    public int getUserInputForTransfer(int userInput) {

        try {
            System.out.println("Enter ID of user you are sending to (0 to cancel): ");
            userInput = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
        }


        return userInput;
    }

    public double getTransferAmount(double accountBalance){
        double userInput = -1;
        boolean isValidTransferAmount = false;
        while (isValidTransferAmount == false) {
            System.out.println("Enter amount: ");
            try {
                userInput = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number in the format XX.XX or 0 to exit");
                scanner.next();
                continue;
            }
            if (userInput < 0) {
                System.out.println("Please enter a positive number in the format XX.XX or 0 to exit");
                continue;
            }
            if (userInput > accountBalance){
                System.out.println("Insufficient funds. Enter a new amount or 0 to exit");
            }
            else if(userInput <= accountBalance){
                isValidTransferAmount =true;
            }
        }

        return userInput;
    }

    public void printListOfTransfers(Transfer[] transfers, User currentUser){
        System.out.println("----------------------------------------------------");
        System.out.println("Transfers");
        System.out.printf("%-22s%-22s%-22s\n", "ID", "From/To", "Amount");
        System.out.println("----------------------------------------------------");
        for (Transfer transfer : transfers) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String formattedAmount = formatter.format(transfer.getAmount());
            String fromToUsername;
            if(transfer.getReceivingUsername().equalsIgnoreCase(currentUser.getUsername())){
                fromToUsername = "From: " + transfer.getSendingUsername();
            }else {fromToUsername = "To: " +  transfer.getReceivingUsername();}
            System.out.printf("%-22s%-22s%-22s\n", transfer.getTransferId(), (fromToUsername +  " ") , formattedAmount);
        }
        System.out.println("----------------------------------------------------");
    }

    public Transfer transferDetailsFromUserSelection(Transfer[] transfers){
        int userInput = -1;
        Transfer userSelectedTransfer = null;
        boolean isValidTransferId = false;
        while (isValidTransferId == false) {
            System.out.println("Please enter transfer ID to view details (0 to cancel): ");
            try {
                userInput = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter valid transfer number");
                scanner.next();
                continue;
            }if(userInput == 0){
                break;
            }
            for(Transfer transfer: transfers){
                if(userInput == transfer.getTransferId()){
                    userSelectedTransfer = transfer;
                    isValidTransferId = true;
                }
            }if(userSelectedTransfer == null){
                System.out.println("Transfer Id not found");
            }
        }
        return userSelectedTransfer;
    }

    public void printTransferDetails(Transfer transfer){
        System.out.println("-------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("-------------------------------------");
        System.out.println("Id: " + transfer.getTransferId());
        System.out.println("From: " + transfer.getSendingUsername());
        System.out.println("To: " + transfer.getReceivingUsername());
        if(transfer.getTransferTypeId() == 1){
            System.out.println("Type: Request");
        }
        if(transfer.getTransferTypeId() == 2) {
            System.out.println("Type: Send");
        }
        if(transfer.getTransferStatusId() == 1){
            System.out.println("Status: Pending");
        }
        if(transfer.getTransferStatusId() == 2){
            System.out.println("Status: Approved");
        }
        if(transfer.getTransferStatusId() ==3){
            System.out.println("Status: Rejected");
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String formattedAmount = formatter.format(transfer.getAmount());
        System.out.println("Amount: " + formattedAmount);
    }

}
