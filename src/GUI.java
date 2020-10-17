package DigitalPaymentServices;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class GUI{
    public static FileRW fileUser = new FileRW();
    public static FileRW filePass = new FileRW();
    public static FileRW global = new FileRW();
    public String user;
    public String pass;
    public static boolean isLoggedIn = false;

    void frame(){
        //Initialization events

        JFrame window = new JFrame("Digital Payment Services");
        JPanel login = new JPanel();
        JPanel banking = new JPanel();

        JTextField user = new JTextField();
        JTextField pass = new JTextField();
        JTextField amountField = new JTextField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton deposit = new JButton("Deposit");
        JButton withdraw = new JButton("Withdraw");
        JLabel amount = new JLabel("$");
        JLabel balanceLabel = new JLabel("$0");
        JLabel warnings = new JLabel();
        int centerX = 500;
        int centerY = 550;

        user.setBounds(centerX/2 - 200/2,100,200,25);
        pass.setBounds(centerX/2 - 200/2,130,200,25);
        loginButton.setBounds(centerX/2 - 100/2 - 60,200,100,50);
        registerButton.setBounds(centerX/2 - 100/2 + 60,200,100,50);

        login.setBounds(0, 0, centerX, centerY);
        login.setBackground(Color.lightGray);
        login.setLayout(null);
        login.add(user);
        login.add(loginButton);
        login.add(pass);
        login.add(registerButton);
        login.setVisible(true);
        
        deposit.setBounds(centerX/2 - 100/2 + 60,200,100,50);
        withdraw.setBounds(centerX/2 - 100/2 - 60,200,100,50);
        amountField.setBounds(centerX/2 - 200/2,100,200,25);
        amount.setBounds(centerX/2 - 200/2 - 25,100,100,25);
        balanceLabel.setBounds(centerX/2 - 100/2,10,100,25);
        warnings.setBounds(centerX/2 - 200/2,70,250,25);

        banking.setBounds(0, 0, centerX, centerY);
        banking.setBackground(Color.lightGray);
        banking.setLayout(null);
        banking.add(deposit);
        banking.add(withdraw);
        banking.add(amountField);
        banking.add(amount);
        banking.add(balanceLabel);
        banking.add(warnings);
        banking.setVisible(true);

        window.setSize(centerX, centerY);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.add(login);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileUser.writeFile(user.getText(), fileUser.userList);
                filePass.writeFile(pass.getText(), filePass.passList);
                Security.updateIndex(user.getText());
                System.out.println(Security.userIndex);
            }
        });
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileUser.readFile(fileUser.userList);
                filePass.readFile(filePass.passList);
                Security.password(user.getText(), pass.getText());
                if(isLoggedIn==true){
                    global.balance = "./balances/balance"+Security.userIndex+".txt";
                    try{
                        UpdateStats.updateBalance();
                        global.readBalance(global.balance);
                        balanceLabel.setText("$"+global.balanceGlobal[0]); 
                    }
                    catch(ArrayIndexOutOfBoundsException E){
                        global.writeFile("0", global.balance);
                        UpdateStats.updateBalance();
                        global.readBalance(global.balance);
                    }
                    window.remove(login);
                    window.repaint();
                    window.add(banking);
                    window.validate();
                }
                System.out.println("logged in");   
            }
        });
        deposit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    if(Double.parseDouble(amountField.getText())>0){
                        String tempBal = Double.toString(Double.parseDouble(global.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.writeBalance(Security.userIndex, tempBal, global.balance);
                        System.out.println(global.balance);
                        UpdateStats.updateBalance();
                        balanceLabel.setText("$"+tempBal);
                        warnings.setText("");
                    }
                    else{
                        warnings.setText("Error: Deposits must be greater than $0.");
                    }
                }
                catch(NumberFormatException n){
                    warnings.setText("Error: Deposits must an integer.");
                }
                window.repaint();
            }
        });
        withdraw.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    if(Double.parseDouble(amountField.getText())>0){
                        String tempBal = Double.toString(Double.parseDouble(global.balanceGlobal[0]) - Double.parseDouble(amountField.getText()));
                        global.writeBalance(Security.userIndex, tempBal, global.balance);
                        System.out.println(global.balance);
                        UpdateStats.updateBalance();
                        balanceLabel.setText("$"+tempBal);
                        warnings.setText("");
                    }
                    else{
                        warnings.setText("Error: Withdraws must be greater than $0.");
                    }
                }
                catch(NumberFormatException n){
                    warnings.setText("Error: Withdraws must an integer.");
                }
                window.repaint();
            }
        });

        this.user = user.getText();
        this.pass = pass.getText();
    }
}
class Security{
    public static int userIndex = 0;
    public static void updateIndex(String login){
        try{
            while(!login.equals(GUI.fileUser.output[userIndex])){
                userIndex++;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("User does not exist");
        }
    }
    public static void password(String login, String pass){
        updateIndex(login);
        if(pass.equals(GUI.filePass.output[userIndex])){
            GUI.isLoggedIn = true;
            System.out.println("\nCorrect Password "+Security.userIndex+" pass "+pass+" "+GUI.filePass.output[userIndex]);
        }
        else{
            System.out.println("Incorrect Password "+Security.userIndex+" pass "+pass+" "+GUI.filePass.output[userIndex]);
        }
    }
}
class UpdateStats{
    static void updateBalance(){
        try{
            FileRW updateBal = new FileRW();
            updateBal.readFile(GUI.global.balance);
            // while(Security.userIndex < GUI.global.output.length){
            // }
            // System.out.println("Balance: "+updateBal.output[Security.userIndex]);
            System.out.println("Balance: "+GUI.global.balanceGlobal[0]);
            GUI.global.balanceGlobal[0] = updateBal.output[0];
        }
        catch(Exception e){

        }
    }
}
