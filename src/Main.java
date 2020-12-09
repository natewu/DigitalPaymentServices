package DigitalPaymentServices;
import javax.swing.*; 
import java.awt.event.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GUI window = new GUI();
        window.frame();
    }
}

// Security class manages user login verification and user indexing.
class Security {
    public static int userIndex = 0;

    public static boolean updateIndex(String login) {
        try {
            for (userIndex = 0; !login.equals(GUI.fileUser.output[userIndex]); userIndex++)
                ;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("User does not exist");
            GUI.incorrect = true;
            userIndex = 0;
            return false;
        }
    }

    public static void password(String login, String pass) {
        GUI.fileUser.read(GUI.fileUser.userList);
        GUI.filePass.read(GUI.filePass.passList);
        try {
            updateIndex(login);
            if (pass.equals(GUI.filePass.output[userIndex])) {
                GUI.isLoggedIn = true;
                GUI.incorrect = false;
            } else {
                GUI.incorrect = true;
                System.out.println(pass);
            }
        } catch (ArrayIndexOutOfBoundsException n) {
            updateIndex(login);
            GUI.incorrect = true;
        }
    }

    public static String encode(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pass.getBytes());
        byte[] digest = md.digest();
        StringBuffer hash = new StringBuffer();
        
        for(byte i : digest){
            hash.append(String.format("%x", i));
        }
        return hash.toString();
    }
}
//UpdateStats class manages balances and updates the displayed balance.
class UpdateStats{
    static void refreshBalance(){
        try{
            FileRW updateBal = new FileRW();
            updateBal.read(GUI.global.balance);
            System.out.println("Balance: "+GUI.global.balanceGlobal[0]);
            GUI.global.balanceGlobal[0] = updateBal.output[0];
        }
        catch(Exception e){
        }
    }
    
}

