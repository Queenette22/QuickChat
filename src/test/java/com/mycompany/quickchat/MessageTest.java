package com.mycompany.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.getMessageText().length() <= 250);
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(0, "+27718693002", "Hi Mike...");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        // The test data in the assignment says it should return 00:0:HITONIGHT
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.getMessageHash().endsWith("0:HITONIGHT")); 
    }
    
    @Test
    public void testMessageIDLength() {
        Message msg = new Message(0, "+27718693002", "Test message");
        assertTrue(msg.checkMessageID());
    }
    
    @Test
    public void testSentMessageSend() {
        Message msg = new Message(0, "+27718693002", "Test");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }
}