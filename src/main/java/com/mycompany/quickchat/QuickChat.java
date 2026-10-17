package com.mycompany.quickchat;

import java.util.Scanner;

public class QuickChat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // "Welcome to QuickChat." message required by the assignment
        System.out.println("Welcome to QuickChat.");
        
        // Users define how many messages they wish to enter
        System.out.print("How many messages do you wish to enter? ");
        int numMessagesToEnter = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            // Numeric Menu
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choice: ");
            int menuChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (menuChoice) {
                case 1:
                    if (messagesEntered >= numMessagesToEnter) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }
                    
                    System.out.print("Enter recipient cell number (e.g., +27718693002): ");
                    String recipient = scanner.nextLine();
                    
                    System.out.print("Enter message (max 250 chars): ");
                    String text = scanner.nextLine();
                    
                    // Check message length requirement
                    if (text.length() > 250) {
                        int excess = text.length() - 250;
                        System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                        break;
                    } else {
                         System.out.println("Message ready to send.");
                    }
                    
                    // This will work once we create the Message class next!
                    Message msg = new Message(messagesEntered, recipient, text);
                    
                    // Check recipient validity
                    String cellCheck = msg.checkRecipientCell();
                    System.out.println(cellCheck);
                    if (cellCheck.contains("incorrectly")) break; // Stop if invalid
                    
                    // Print details requirement
                    System.out.println("Message ID generated: " + msg.getMessageID());
                    System.out.println("Message Hash: " + msg.getMessageHash());
                    System.out.println("Recipient: " + msg.getRecipient());
                    System.out.println("Message: " + msg.getMessageText());

                    // Post-message menu requirement
                    System.out.println("\nWhat do you want to do?");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choice: ");
                    int actionChoice = scanner.nextInt();
                    
                    String actionResult = msg.SentMessage(actionChoice);
                    System.out.println(actionResult);
                    
                    if (actionChoice == 1 || actionChoice == 3) {
                        messagesEntered++;
                    }
                    break;
                    
                case 2:
                    // "This feature is still in development"
                    System.out.println("Coming Soon.");
                    break;
                    
                case 3:
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid option.");
            }
        }
        
        // The total number of messages should be accumulated
        System.out.println("\nTotal messages sent: " + Message.returnTotalMessagess());
        
        scanner.close();
    }
}