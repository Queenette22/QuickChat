package com.mycompany.quickchat;

import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    // Static lists to keep track of messages while the app is running
    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();
    public static int totalMessagesSent = 0;

    // Constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // 1. Generates a random 10-digit ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // 2. Check Message ID length
    public boolean checkMessageID() {
        // Returns true if the ID is 10 or fewer characters
        return this.messageID.length() <= 10;
    }

    // 3. Check Recipient Cell format
    public String checkRecipientCell() {
        // Valid if it starts with "+" and has an international code (length check)
        // The test data uses "+27718693002" which is 12 characters.
        if (this.recipient.startsWith("+") && this.recipient.length() >= 11) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // 4. Create the Message Hash
    public String createMessageHash() {
        // First two numbers of ID : Message Number : First and Last word in ALL CAPS
        // Example: 00:0:HITONIGHT
        String firstTwoID = this.messageID.substring(0, 2);
        
        // Split message into words to get first and last
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        
        // Remove punctuation from the last word (e.g., "tonight?")
        lastWord = lastWord.replaceAll("[^a-zA-Z]", ""); 

        return firstTwoID + ":" + this.messageNumber + ":" + firstWord + lastWord;
    }
    
    // 5. Handle the Send/Store/Disregard menu choice
    public String SentMessage(int choice) {
        switch (choice) {
            case 1: // Send
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2: // Disregard
                return "Press 0 to delete the message.";
            case 3: // Store
                storedMessages.add(this);
                storeMessage(); // Calls the JSON method
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    // 6. Print all sent messages
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n\n");
        }
        return sb.toString();
    }

    // 7. Return Total Messages Sent
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // 8. Research: Store Message in a JSON file format
    public void storeMessage() {
        // Creating a basic JSON formatted string
        String jsonString = "{\n" +
                "  \"MessageID\": \"" + this.messageID + "\",\n" +
                "  \"MessageHash\": \"" + this.messageHash + "\",\n" +
                "  \"Recipient\": \"" + this.recipient + "\",\n" +
                "  \"Message\": \"" + this.messageText + "\"\n" +
                "}\n";

        // Write the string to a file named "messages.json"
        try (FileWriter file = new FileWriter("messages.json", true)) { // 'true' means append to file
            file.write(jsonString);
            file.write("\n"); // Add a line break for the next message
        } catch (IOException e) {
            System.out.println("An error occurred while saving the JSON file.");
            e.printStackTrace();
        }
    }
    
    // --- GETTERS (Needed for the Main class to print details) ---
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
}