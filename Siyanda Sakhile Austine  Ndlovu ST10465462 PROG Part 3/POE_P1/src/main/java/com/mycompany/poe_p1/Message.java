/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_p1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import javax.swing.JOptionPane;

/**
 *
 * @author Siyanda
 */
class Message {

    static class SentMessage {

        String sender;
        String recipient;
        String content;
        String messageID;
        String messageHash;

        SentMessage(String sender, String recipient, String content, String messageID, String messageHash) {
            this.sender = sender;
            this.recipient = recipient;
            this.content = content;
            this.messageID = messageID;
            this.messageHash = messageHash;
        }

        public String toString() {
            return "Sender: " + sender + "\nRecipient: " + recipient + "\nMessage: " + content
                    + "\nMessage ID: " + messageID + "\nHash: " + messageHash;
        }
    }

    List<SentMessage> sentMessages = new ArrayList<>();
    List<String> disregardedMessages = new ArrayList<>();
    List<String> storedMessages = new ArrayList<>();
    List<String> messageHashes = new ArrayList<>();
    List<String> messageIDs = new ArrayList<>();

    public boolean checkRecipientCell(String recipientCell) {
        return recipientCell.matches("\\+27[6-8]\\d{8}");
    }

    public String createMessageHash(String messageContent) {
        String hash = UUID.nameUUIDFromBytes(messageContent.getBytes()).toString();
        messageHashes.add(hash);
        return hash;
    }

    public String generateMessageID(Set<String> existingIDs) {
        Random random = new Random();
        String id;
        do {
            id = String.format("%010d", Math.abs(random.nextLong() % 1_000_000_0000L));
        } while (existingIDs.contains(id));
        existingIDs.add(id);
        messageIDs.add(id);
        return id;
    }

    public void readStoredMessagesFromFile(String filename) {
        try ( Scanner reader = new Scanner(new File(filename))) {
            while (reader.hasNextLine()) {
                storedMessages.add(reader.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading stored messages: " + e.getMessage());
        }
    }

    public void displaySendersAndRecipients() {
        
        for (SentMessage msg : sentMessages) {
            JOptionPane.showMessageDialog(null,"Sender: " + msg.sender + "\nRecipient: " + msg.recipient + "\nMessage ID: " + msg.messageID);
        }
    }

    public void displayLongestMessage() {
        SentMessage longest = null;
        for (SentMessage msg : sentMessages) {
            if (longest == null || msg.content.length() > longest.content.length()) {
                longest = msg;
            }
        }
        if (longest != null) {
            JOptionPane.showMessageDialog(null, "Longest Message: " + longest);
        } else {
            JOptionPane.showMessageDialog(null,"No sent messages available.");
        }
    }

    public void searchByMessageID(String id) {
        for (SentMessage msg : sentMessages) {
            if (msg.messageID.equals(id)) {
                JOptionPane.showMessageDialog(null,"Found: " + msg);
                return;
            }
        }
        JOptionPane.showMessageDialog(null,"Message ID not found.");
    }

    public void searchByRecipient(String recipient) {
        boolean found = false;
        for (SentMessage msg : sentMessages) {
            if (msg.recipient.equals(recipient)) {
                JOptionPane.showMessageDialog(null,msg);
                found = true;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null,"No messages found for recipient: " + recipient);
        }
    }

    public void deleteByHash(String hash) {
        Iterator<SentMessage> iterator = sentMessages.iterator();
        boolean deleted = false;
        while (iterator.hasNext()) {
            SentMessage msg = iterator.next();
            if (msg.messageHash.equals(hash)) {
                iterator.remove();
                deleted = true;
                JOptionPane.showMessageDialog(null,"Message deleted.");
                break;
            }
        }
        if (!deleted) {
            JOptionPane.showMessageDialog(null,"Hash not found.");
        }
    }

    public void displayReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No messages sent.");
        } else {
            JOptionPane.showMessageDialog(null,"=== Message Report ===");
            for (SentMessage msg : sentMessages) {
                JOptionPane.showMessageDialog(null,msg);
            }
        }
    }

    private String generateMessageID() {
        return String.format("%010d", Math.abs(new Random().nextLong() % 1_000_000_0000L));
    }

    public String sentMessage(String sender, String recipient, String message, String option) {
        String messageID = generateMessageID();
        String messageHash = createMessageHash(message);

        switch (option.toLowerCase()) {
            case "send":
                SentMessage sent = new SentMessage(sender, recipient, message, messageID, messageHash);
                sentMessages.add(sent);
                messageIDs.add(messageID);
                messageHashes.add(messageHash);
                return "Message sent.";
            case "store":
                storedMessages.add(message);
                return "Message stored.";
            case "disregard":
                disregardedMessages.add(message);
                return "Message disregarded.";
            default:
                return "Invalid option.";
        }
    }

}
