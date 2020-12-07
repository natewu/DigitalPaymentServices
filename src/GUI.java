package DigitalPaymentServices;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class GUI{
    //Public variables with static variables that can be accessed globally.
    public static FileRW fileUser = new FileRW();
    public static FileRW filePass = new FileRW();
    public static BalanceRW global = new BalanceRW();
    public String user;
    public String pass;
    public static boolean isLoggedIn = false;
    boolean active = false;
    static boolean incorrect = false;

    //GUI Frame and panels
    JFrame window = new JFrame("Digital Payment Services");
    JPanel login = new JPanel();
    JPanel banking = new JPanel();
    JPanel transfer = new JPanel();

    //Login panel elements
    JLabel username = new JLabel("<html><p style: font-family:'Arial'; font-size: 14;>Username</p>");
    JLabel password = new JLabel("<html><p style: font-family:'Arial'; font-size: 14;>Password</p>");
    JLabel incorrectJLabel = new JLabel();
    JTextField userField = new JTextField();
    JTextField passField = new JTextField();
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    //Banking panel elements
    JTextField amountField = new JTextField();
    JButton deposit = new JButton("Deposit");
    JButton withdraw = new JButton("Withdraw");
    JButton send = new JButton("Transfer Money");
    JButton logout = new JButton("Logout");
    JLabel amount = new JLabel("$");
    JLabel balanceLabel = new JLabel("Balance: $0");
    JLabel warnings = new JLabel();
    JLabel userId = new JLabel("User ID: ");

    //Transfer panel elements
    JTextField idField = new JTextField();
    JButton sendFunds = new JButton("Transfer");
    JLabel userIdLabel = new JLabel("User ID: ");

    void frame(){
        //Initialization events
        int centerX = 400;
        int centerY = 500;

        //Login Panel
        username.setBounds(centerX/2 - 280/2,75,280,25);
        incorrectJLabel.setBounds(centerX/2 - 280/2,50,280,25);
        password.setBounds(centerX/2 - 280/2,130,280,25);
        userField.setBounds(centerX/2 - 280/2,100,280,25);
        passField.setBounds(centerX/2 - 280/2,155,280,25);
        loginButton.setBounds(centerX/2 - 130/2 - 75,250,130,60);
        registerButton.setBounds(centerX/2 - 130/2 + 75,250,130,60);

        login.setBounds(0, 0, centerX, centerY);
        login.setBackground(Color.white);
        login.setLayout(null);
        login.add(username);
        login.add(password);
        login.add(userField);
        login.add(loginButton);
        login.add(passField);
        login.add(registerButton);
        login.add(incorrectJLabel);
        loginButton.setMnemonic(KeyEvent.VK_ENTER);
        login.setVisible(true);
        
        //Banking Panel
        deposit.setBounds(centerX/2 - 105/2 + 55,200,105,50);
        withdraw.setBounds(centerX/2 - 105/2 - 55,200,105,50);
        send.setBounds(centerX/2 - 215/2,260,215,50);
        logout.setBounds(centerX/2 - 215/2,330,215,50);
        amountField.setBounds(centerX/2 - 200/2,100,200,25);
        amount.setBounds(centerX/2 - 200/2 - 25,100,100,25);
        balanceLabel.setBounds(centerX/2 - 200/2,30,200,25);
        warnings.setBounds(centerX/2 - 300/2,70,300,25);
        userId.setBounds(centerX/2 - 100/2,420,100,25);

        banking.setBounds(0, 0, centerX, centerY);
        banking.setBackground(Color.white);
        banking.setLayout(null);
        banking.setVisible(true);
        banking.add(deposit);
        banking.add(withdraw);
        banking.add(send);
        banking.add(logout);
        banking.add(amountField);
        banking.add(amount);
        banking.add(balanceLabel);
        banking.add(warnings);
        banking.add(userId);

        //Transfer Panel
        idField.setBounds(centerX/2 - 200/2,155,200,25);
        sendFunds.setBounds(centerX/2 - 100/2,200,100,50);
        userIdLabel.setBounds(centerX/2 - 100/2-50,120,100,50);
        transfer.setBounds(0, 0, centerX, centerY);
        transfer.setBackground(Color.white);
        transfer.setLayout(null);
        transfer.setVisible(true);

        transfer.add(sendFunds);
        transfer.add(userIdLabel);
        transfer.add(idField);        

        //GUI Style
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        warnings.setFont(new Font("Serif", Font.PLAIN, 12));
        warnings.setForeground(Color.RED);
        incorrectJLabel.setForeground(Color.RED);
        incorrectJLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        userId.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setForeground(new Color(255,255,255));
        loginButton.setBackground(new Color(83, 211, 209));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.setForeground(new Color(255,255,255));
        registerButton.setBackground(new Color(83, 211, 209));
        registerButton.setBorder(BorderFactory.createEmptyBorder());
        deposit.setFont(new Font("Arial", Font.BOLD, 15));
        deposit.setForeground(new Color(255,255,255));
        deposit.setBackground(new Color(83, 211, 209));
        deposit.setBorder(BorderFactory.createEmptyBorder());
        withdraw.setFont(new Font("Arial", Font.BOLD, 15));
        withdraw.setForeground(new Color(255,255,255));
        withdraw.setBackground(new Color(83, 211, 209));
        withdraw.setBorder(BorderFactory.createEmptyBorder());
        send.setFont(new Font("Arial", Font.BOLD, 15));
        send.setForeground(new Color(255,255,255));
        send.setBackground(new Color(83, 211, 209));
        send.setBorder(BorderFactory.createEmptyBorder());
        logout.setFont(new Font("Arial", Font.BOLD, 15));
        logout.setForeground(new Color(255,255,255));
        logout.setBackground(new Color(83, 211, 209));
        logout.setBorder(BorderFactory.createEmptyBorder());
        sendFunds.setFont(new Font("Arial", Font.BOLD, 15));
        sendFunds.setForeground(new Color(255,255,255));
        sendFunds.setBackground(new Color(83, 211, 209));
        sendFunds.setBorder(BorderFactory.createEmptyBorder());

        //Main Frame
        window.setSize(centerX, centerY);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.add(login);
        window.getRootPane().setDefaultButton(loginButton);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Button event listeners
        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Writes the data in user and password fields to their respective files.
                fileUser.write(userField.getText(), fileUser.userList);
                filePass.write(passField.getText(), filePass.passList);
                //Updates the userindex
                Security.updateIndex(userField.getText());
            }
        });
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Reads the list of usernames and passwords to be compared by Security.password();
                fileUser.read(fileUser.userList);
                filePass.read(filePass.passList);
                Security.password(userField.getText(), passField.getText());
                //Checks for valid input
                if(incorrect==true){
                    incorrectJLabel.setText("Incorrect username or password!");
                }
                //Validates if user is logged in before updating the window to show banking information
                else if(isLoggedIn==true){
                    global.balance = "./balances/balance"+Security.userIndex+".txt";
                    try{
                        UpdateStats.refreshBalance();
                        global.read(global.balance);
                        balanceLabel.setText("Balance: $"+global.balanceGlobal[0]); 
                        userId.setText("User ID: "+Security.userIndex);
                    }
                    //If user is newly registered, creates a file to store the user balance
                    catch(ArrayIndexOutOfBoundsException E){
                        global.write("0", global.balance);
                        UpdateStats.refreshBalance();
                        global.read(global.balance);
                    }
                    warnings.setText("");
                    incorrectJLabel.setText("");
                    amountField.setText("");
                    window.remove(login);
                    window.repaint();
                    window.add(banking);
                    window.validate();
                } 
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
                //Switches between the banking and transfer panel, send(JButton) is a toggle, uses boolean to check toggle state
                if(active==false){
                    window.remove(banking);
                    window.repaint();
                    transfer.add(send);
                    transfer.add(amount);
                    transfer.add(amountField);
                    transfer.add(balanceLabel);
                    transfer.add(warnings);
                    transfer.add(userId);
                    amountField.setText("");
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
                    banking.add(userId);
                    amountField.setText("");
                    send.setText("Transfer Money");
                    send.setBounds(centerX/2 - 215/2,260,215,50);
                    window.add(banking);
                    window.validate();
                    active = false;
                }
            }
        });
        //Logs the user out and clears the global user index to reset session
        logout.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                isLoggedIn = false;
                window.remove(banking);
                window.repaint();
                global = null;
                global = new BalanceRW();
                window.add(login);
                window.validate();
            }
        });
        //Sends the funds to the user ID specified
        sendFunds.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    int id = Integer.parseInt(idField.getText());
                    warnings.setText("");
                    updateBalance(id, "Transfer");
                }
                catch(NumberFormatException n){
                    if(idField.getText().isBlank()){
                        warnings.setText("Error: Please enter user ID to be transferred to.");
                    }
                }
            }
        });
        this.user = userField.getText();
        this.pass = passField.getText();
    }
    
    //Function to update balance
    void updateBalance(int id, String type){
        try{
            //Constructor creates new object so the previous data is always cleared.
            BalanceRW transferRW = new BalanceRW();
            transferRW.read("./balances/balance"+id+".txt");
            //Switch statement allows to choose what should be performed
            switch(type){
                case "Withdraw" , "Transfer":
                    //Checks if user has enough balance before being withdrawn or transferred to another user and if the amoount is greater than 0.
                    if(Double.parseDouble(amountField.getText())>0 && Double.parseDouble(amountField.getText()) - Double.parseDouble(global.balanceGlobal[0])<=0){
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.write(tempBal, "./balances/balance"+id+".txt");
                        String tempTransfer = Double.toString(Double.parseDouble(global.balanceGlobal[0]) - Double.parseDouble(amountField.getText()));
                        global.write(tempTransfer, global.balance);
                        UpdateStats.refreshBalance();
                        balanceLabel.setText("Balance: $"+tempTransfer);
                        transferRW = null; // deletes data 
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
                    //Check if the amount deposited is greater than 0.
                    if(Double.parseDouble(amountField.getText())>0){
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.write(tempBal, global.balance);
                        UpdateStats.refreshBalance();
                        balanceLabel.setText("Balance: $"+tempBal);
                        transferRW = null; // deletes data
                        warnings.setText("");
                    }
                    else{
                        warnings.setText("Error: "+type+"s must be greater than $0.");
                    }
                    break;
            }
        }
        //Displays error if user enters something other than a numerical value.
        catch(NumberFormatException n){
            warnings.setText("Error: "+type+"s must a numerical value.");
        }
        //Refreshes window to display updated information
        window.repaint();
    }
}
