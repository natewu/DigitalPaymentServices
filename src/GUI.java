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
    boolean active = false;

    JFrame window = new JFrame("Digital Payment Services");
    JPanel login = new JPanel();
    JPanel banking = new JPanel();
    JPanel transfer = new JPanel();

    JTextField userField = new JTextField();
    JTextField passField = new JTextField();
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    JTextField amountField = new JTextField();
    JButton deposit = new JButton("Deposit");
    JButton withdraw = new JButton("Withdraw");
    JButton send = new JButton("Transfer Money");
    JLabel amount = new JLabel("$");
    JLabel balanceLabel = new JLabel("$0");
    JLabel warnings = new JLabel();
    JLabel userId = new JLabel("ID: ");

    JTextField idField = new JTextField();
    JButton sendFunds = new JButton("Transfer");

    void frame(){
        //Initialization events

        
        int centerX = 400;
        int centerY = 550;

        //Login Panel
        
        userField.setBounds(centerX/2 - 200/2,100,200,25);
        passField.setBounds(centerX/2 - 200/2,130,200,25);
        loginButton.setBounds(centerX/2 - 100/2 - 60,200,100,50);
        registerButton.setBounds(centerX/2 - 100/2 + 60,200,100,50);

        login.setBounds(0, 0, centerX, centerY);
        login.setBackground(Color.lightGray);
        login.setLayout(null);
        login.add(userField);
        login.add(loginButton);
        login.add(passField);
        login.add(registerButton);
        login.setVisible(true);
        
        //Banking Panel
        
        deposit.setBounds(centerX/2 - 105/2 + 55,200,105,50);
        withdraw.setBounds(centerX/2 - 105/2 - 55,200,105,50);
        send.setBounds(centerX/2 - 215/2,260,215,50);
        amountField.setBounds(centerX/2 - 200/2,100,200,25);
        amount.setBounds(centerX/2 - 200/2 - 25,100,100,25);
        balanceLabel.setBounds(centerX/2 - 100/2,10,100,25);
        warnings.setBounds(centerX/2 - 200/2,70,300,25);
        userId.setBounds(centerX/2 - 100/2,30,100,25);

        banking.setBounds(0, 0, centerX, centerY);
        banking.setBackground(Color.lightGray);
        banking.setLayout(null);
        banking.setVisible(true);
        banking.add(deposit);
        banking.add(withdraw);
        banking.add(send);
        banking.add(amountField);
        banking.add(amount);
        banking.add(balanceLabel);
        banking.add(warnings);
        banking.add(userId);
        

        //Transfer Panel
       
        idField.setBounds(centerX/2 - 200/2,130,200,25);
        sendFunds.setBounds(centerX/2 - 100/2,200,100,50);
        transfer.setBounds(0, 0, centerX, centerY);
        transfer.setBackground(Color.lightGray);
        transfer.setLayout(null);
        transfer.setVisible(true);

        transfer.add(sendFunds);
        transfer.add(idField);        

        //Main Frame
        window.setSize(centerX, centerY);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.add(login);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileUser.writeFile(userField.getText(), fileUser.userList);
                filePass.writeFile(passField.getText(), filePass.passList);
                Security.updateIndex(userField.getText());
                System.out.println(Security.userIndex);
            }
        });
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileUser.readFile(fileUser.userList);
                filePass.readFile(filePass.passList);
                Security.password(userField.getText(), passField.getText());
                if(isLoggedIn==true){
                    global.balance = "./balances/balance"+Security.userIndex+".txt";
                    try{
                        UpdateStats.refreshBalance();
                        global.readBalance(global.balance);
                        balanceLabel.setText("$"+global.balanceGlobal[0]); 
                        userId.setText("ID: "+Security.userIndex);
                    }
                    catch(ArrayIndexOutOfBoundsException E){
                        global.writeFile("0", global.balance);
                        UpdateStats.refreshBalance();
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
                updateBalance(Security.userIndex, "Deposit");
            }
        });
        withdraw.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                updateBalance(Security.userIndex, "Withdraw");
            }
        });
        send.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(active==false){
                    window.remove(banking);
                    window.repaint();
                    transfer.add(send);
                    transfer.add(amount);
                    transfer.add(amountField);
                    transfer.add(balanceLabel);
                    transfer.add(warnings);
                    send.setText("Back");
                    send.setBounds(centerX/2 - 100/2,260,100,50);
                    window.add(transfer);
                    window.validate();
                    active = true;
                }
                else if(active == true){
                    window.remove(transfer);
                    window.repaint();
                    banking.add(send);
                    banking.add(amount);
                    banking.add(amountField);
                    banking.add(balanceLabel);
                    banking.add(warnings);
                    send.setText("Transfer Money");
                    send.setBounds(centerX/2 - 215/2,260,215,50);
                    window.add(banking);
                    window.validate();
                    active = false;
                }
                
            }
        });
        sendFunds.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                updateBalance(Integer.parseInt(idField.getText()), "Transfer");
            }
        });

        this.user = userField.getText();
        this.pass = passField.getText();
    }
    
    void updateBalance(int id, String type){
        try{
            FileRW transferRW = new FileRW();
            transferRW.readBalance("./balances/balance"+id+".txt");
            switch(type){
                case "Withdraw" , "Transfer":

                    if(Double.parseDouble(amountField.getText())>0 && Double.parseDouble(amountField.getText()) - Double.parseDouble(global.balanceGlobal[0])<=0){
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.writeBalance(id, tempBal, "./balances/balance"+id+".txt");
                        String tempTransfer = Double.toString(Double.parseDouble(global.balanceGlobal[0]) - Double.parseDouble(amountField.getText()));
                        global.writeBalance(Security.userIndex, tempTransfer, global.balance);
                        UpdateStats.refreshBalance();
                        balanceLabel.setText("$"+tempTransfer);
                        transferRW = null;
                        warnings.setText("");
                    }
                    else if(Double.parseDouble(amountField.getText()) - Double.parseDouble(global.balanceGlobal[0])<=0){
                        warnings.setText("Error: "+type+"s must be greater than $0.");
                    }
                    else{
                        warnings.setText("Error: "+type+"s greater than available funds.");
                    }
                    
                    break;
                case "Deposit":
                    if(Double.parseDouble(amountField.getText())>0){
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.writeBalance(Security.userIndex, tempBal, global.balance);
                        UpdateStats.refreshBalance();
                        balanceLabel.setText("$"+tempBal);
                        transferRW = null;
                        warnings.setText("");
                    }
                    else{
                        warnings.setText("Error: "+type+"s must be greater than $0.");
                    }
                    
                    break;
            }
        }
        catch(NumberFormatException n){
            warnings.setText("Error: "+type+"s must an integer.");
        }
        window.repaint();
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
