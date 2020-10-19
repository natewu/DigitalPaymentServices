package DigitalPaymentServices;
import javax.swing.*; 
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        GUI window = new GUI();
        window.frame();
    }
}

//Security class manages user login verification and user indexing.
class Security{
    public static int userIndex = 0;
    public static void updateIndex(String login){
        try{
            for(userIndex = 0; !login.equals(GUI.fileUser.output[userIndex]); userIndex++);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("User does not exist");
            GUI.incorrect = true;
        }
    }
    public static void password(String login, String pass){
        try{
            updateIndex(login);
            if(pass.equals(GUI.filePass.output[userIndex])){
                GUI.isLoggedIn = true;
                System.out.println("\nCorrect Password "+Security.userIndex+" pass "+pass+" "+GUI.filePass.output[userIndex]);
            }
            else{
                GUI.incorrect = true;
                System.out.println("Incorrect Password "+Security.userIndex+" pass "+pass+" "+GUI.filePass.output[userIndex]);
            }
        }
        catch(ArrayIndexOutOfBoundsException n){
            updateIndex(login);
            GUI.incorrect = true;
        }
        
    }
}
//UpdateStats class manages balances and updates the displayed balance.
class UpdateStats{
    static void refreshBalance(){
        try{
            FileRW updateBal = new FileRW();
            updateBal.readFile(GUI.global.balance);
            System.out.println("Balance: "+GUI.global.balanceGlobal[0]);
            GUI.global.balanceGlobal[0] = updateBal.output[0];
        }
        catch(Exception e){
        }
    }
    
}

