/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_p1;

/**
 *
 * @author Siyanda
 */
class Login {
    public boolean checkUsername(String username){     //check username
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password){    //check username
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[0-9].*") && password.matches(".*[!@#$%^&+=?].*");
    }
    
    public boolean checkPhoneNumber(String phone_number){     //check phone number
        return phone_number.matches("\\+27[6-8]\\d{8}");
        /*Referance
          OpenAI. (2023). ChatGPT (Mar 14 version) [Large language model].
          https://chat.openai.com/chat
        */
    }
    
    public String registerUser(String firstName, String lastName){
        if(firstName.isEmpty() && lastName.isEmpty()){
            return null;
        }else{
            return "";
        }
       
    }
    
    public boolean userLogin(String username, String validUsername, String password, String validPassword){
        return validUsername.equals(username) && validPassword.equals(password); 
    }
    
}
    
    
