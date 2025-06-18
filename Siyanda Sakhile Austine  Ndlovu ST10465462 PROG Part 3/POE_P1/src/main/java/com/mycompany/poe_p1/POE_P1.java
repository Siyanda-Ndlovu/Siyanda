/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.poe_p1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Siyanda
 */
public class POE_P1 {

    public static void main(String[] args) {
        Login login = new Login();     //creating a Login class
        Scanner scan = new Scanner(System.in);    //creating a scanner

        String username;  //declaring username and password
        String password;
        String firstName;
        String lastName;
        JOptionPane.showMessageDialog(null, "=====User registration=====");

        while (true) {
            //Registering a user
            firstName = JOptionPane.showInputDialog(null, "Enter Your First Name: ");
            if (firstName.length() < 0) {
                JOptionPane.showMessageDialog(null, "First name can't be empty");

            }
            break;
        }

        while (true) {
            lastName = JOptionPane.showInputDialog(null, "Enter Your Last Name: ");
            if (lastName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Last name can't be empty");
                return;
            }
            break;

        }
        //myObj.registerUser(lastName, lastName);
        while (true) {    //loop until user enters a true value
            username = JOptionPane.showInputDialog(null, "Please enter your username: ");
            //username = scan.nextLine();

            if (login.checkUsername(username)) {       //calling checkUsername from a login class
                JOptionPane.showMessageDialog(null, "Username successfully captured");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
            }
        }

        while (true) {
            password = JOptionPane.showInputDialog(null, "Please enter your password: ");
            //password = scan.nextLine();

            if (login.checkPasswordComplexity(password)) {     //calling checkPassword from a login class
                JOptionPane.showMessageDialog(null, "Password successfully captured.");    //output for a true password
                break;    //when the user has entered the true value, it must break and go to the next task
            } else {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
            }
        }

        while (true) {
            String phone_number = JOptionPane.showInputDialog(null, "Please enter your Cellphone number: ");
            //String phone_number = scan.nextLine();

            if (login.checkPhoneNumber(phone_number)) {       //calling checkPhoneNumber from a login class
                JOptionPane.showMessageDialog(null, "Cellphone number successfully addedd.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code.\n"); //output for a false Cellphone numbers
            }
        }

        JOptionPane.showMessageDialog(null, "\n=====User Login=====");

        //it must allow a user to login
        while (true) {
            String validUsername = JOptionPane.showInputDialog(null, "Please enter your username: ");
            //String validUsername = scan.nextLine();     //checkUsername must be the same with username

            String validPassword = JOptionPane.showInputDialog(null, "Please enter your password: ");
            //String validPassword = scan.nextLine();    //checkPassword must be the same with password

            if (login.userLogin(username, validUsername, password, validPassword)) {    //username and password must be the same with the registered username and password
                JOptionPane.showMessageDialog(null, "Welcome " + firstName + ", " + lastName + ", " + "it is great to see you again.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again\n");
            }
        }

        Message theMessage = new Message();   //creating a message class

        
        String myMessage = "";
        String recipientCell = "";
        int count;
        while (true) {
            String[] menu = {"Send Message", "Show Recently Sent Messages", "Quit"};
            int option = JOptionPane.showOptionDialog(null, "Choose an option:", "===Welcome to QuickChat===",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, menu, menu[0]);

            switch (option) {
                case 0 -> {
                    // SENDING MESSAGE

                    Random random = new Random();
                    Set<String> id = new HashSet<>();
                    while (true) {
                        recipientCell = JOptionPane.showInputDialog(null, "Enter recipient's cellphone number:");
                        if (theMessage.checkRecipientCell(recipientCell)) {
                            break;
                        }
                        JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code.\n");
                    }
                    while(true){
                        try{
                            count = Integer.parseInt(JOptionPane.showInputDialog(null, "How many messages do you want to send?"));
                            break;
                        }catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null,"Please enter numbers only");
                        }
                    }
                    
                    for (int i = 1; i <= count; i++) {
                        String message = JOptionPane.showInputDialog(null, "Enter message " + i + " (max 250 characters):");
                        if (message.length() > 0 && message.length() <= 250) {
                            String action = JOptionPane.showInputDialog(null, " Do you want to Send, Store, or Disregard the message?");
                            String result = theMessage.sentMessage(firstName + " " + lastName, recipientCell, message, action);
                            JOptionPane.showMessageDialog(null, result);
                        } else {
                            JOptionPane.showMessageDialog(null, "Please enter a message of less than 50 characters");
                            i--; // retry
                        }

                        String messageID;
                        do {

                            messageID = String.format("%010d", Math.abs(random.nextLong() % 1_000_000_0000L));
                        } while (!id.add(messageID)); // Add and check uniqueness
                        
                        JOptionPane.showMessageDialog(null, "Your Message = " + message);
                        JOptionPane.showMessageDialog(null, "Message ID: " + messageID);
                    }
                }
                case 1 -> {
                    // SENT MESSAGES OPERATIONS
                    String choose = """
                            1. Show all senders and recipients
                            2. Show the longest sent message
                            3. Search by message ID
                            4. Search messages by recipient
                            5. Delete message by hash
                            6. Display full message report
                            7. Go back
                            """;
                    int choice = Integer.parseInt(JOptionPane.showInputDialog(null, choose + "\nChoose an option:"));

                    switch (choice) {
                        case 1 ->
                            theMessage.displaySendersAndRecipients();
                        case 2 ->
                            theMessage.displayLongestMessage();
                        case 3 -> {
                            String id = JOptionPane.showInputDialog(null, "Enter message ID:");
                            theMessage.searchByMessageID(id);
                        }
                        case 4 -> {
                            String r = JOptionPane.showInputDialog(null, "Enter recipient phone number:");
                            theMessage.searchByRecipient(r);
                        }
                        case 5 -> {
                            String hash = JOptionPane.showInputDialog(null, "Enter message hash to delete:");
                            theMessage.deleteByHash(hash);
                        }
                        case 6 ->
                            theMessage.displayReport();
                        case 7 -> {
                            // go back
                        }
                        default ->
                            JOptionPane.showMessageDialog(null, "Invalid choice.");
                    }
                }
                case 2 -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    return;
                }
                default ->
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            } if (option == 2) break;
        }

        try {

            File myObj = new File("message.json");
            if (myObj.createNewFile()) {

                System.out.println("File created: " + myObj.getName());

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        try {
            FileWriter myFile = new FileWriter("message.json");
            myFile.write(recipientCell + " " + myMessage + "\n");
            myFile.close();
            System.out.println("Successfully wrote the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("message.json");
            Scanner myScanner = new Scanner(myObj);
            while (myScanner.hasNextLine()) {
                String data = myScanner.nextLine();
                System.out.println(data);

            }
            myScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
