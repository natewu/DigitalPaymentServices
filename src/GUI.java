package DigitalPaymentServices;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.awt.*;

class GUIDefinitions{
    // Public variables with static variables that can be accessed globally.
    public static FileRW fileUser = new FileRW();
    public static FileRW filePass = new FileRW();
    public static BalanceRW global = new BalanceRW();
    public String user;
    public String pass;
    public static boolean isLoggedIn = false;
    boolean active = false;
    static boolean incorrect = false;

    // GUI Frame and panels
    static JFrame window = new JFrame("Digital Payment Services");
    JPanel login = new JPanel();
    JPanel banking = new JPanel();
    JPanel transfer = new JPanel();

    // Login panel elements
    static JLabel username = new JLabel("<html><p style: font-family:'Arial'; font-size: 14;>Username</p>");
    static JLabel password = new JLabel("<html><p style: font-family:'Arial'; font-size: 14;>Password</p>");
    static JLabel incorrectJLabel = new JLabel();
    static JTextField userField = new JTextField();
    static JPasswordField passField = new JPasswordField();
    static JButton loginButton = new JButton("Login");
    static JButton registerButton = new JButton("Register");
    static JCheckBox showPass = new JCheckBox("Show Password");

    // Banking panel elements
    static JTextField amountField = new JTextField();
    static JButton deposit = new JButton("Deposit");
    static JButton withdraw = new JButton("Withdraw");
    static JButton send = new JButton("Transfer Money");
    static JButton logout = new JButton("Logout");
    static JLabel amount = new JLabel("$");
    static JLabel balanceLabel = new JLabel("Balance: $0");
    static JLabel warnings = new JLabel();
    static JLabel userId = new JLabel("User ID: ");

    // Transfer panel elements
    static JTextField idField = new JTextField();
    static JButton sendFunds = new JButton("Transfer");
    static JLabel userIdLabel = new JLabel("User ID: ");
}

public class GUI extends GUIDefinitions{
    void frame() {
        // Initialization events
        int centerX = 400;
        int centerY = 500;

        // Login Panel
        username.setBounds(centerX / 2 - 280 / 2, 75, 280, 25);
        incorrectJLabel.setBounds(centerX / 2 - 280 / 2, 50, 280, 25);
        password.setBounds(centerX / 2 - 280 / 2, 130, 280, 25);
        userField.setBounds(centerX / 2 - 280 / 2, 100, 280, 25);
        passField.setBounds(centerX / 2 - 280 / 2, 155, 280, 25);
        showPass.setBounds(centerX / 2 - 280 / 2, 183, 280, 25);
        loginButton.setBounds(centerX / 2 - 130 / 2 - 75, 250, 130, 60);
        registerButton.setBounds(centerX / 2 - 130 / 2 + 75, 250, 130, 60);

        login.setBounds(0, 0, centerX, centerY);
        login.setBackground(Color.white);
        login.setLayout(null);
        login.add(username);
        login.add(password);
        login.add(userField);
        login.add(loginButton);
        login.add(passField);
        login.add(showPass);
        login.add(registerButton);
        login.add(incorrectJLabel);
        loginButton.setMnemonic(KeyEvent.VK_ENTER);
        login.setVisible(true);

        // Banking Panel
        deposit.setBounds(centerX / 2 - 105 / 2 + 55, 200, 105, 50);
        withdraw.setBounds(centerX / 2 - 105 / 2 - 55, 200, 105, 50);
        send.setBounds(centerX / 2 - 215 / 2, 260, 215, 50);
        logout.setBounds(centerX / 2 - 215 / 2, 330, 215, 50);
        amountField.setBounds(centerX / 2 - 200 / 2, 100, 200, 25);
        amount.setBounds(centerX / 2 - 200 / 2 - 25, 100, 100, 25);
        balanceLabel.setBounds(centerX / 2 - 200 / 2, 30, 200, 25);
        warnings.setBounds(centerX / 2 - 300 / 2, 70, 300, 25);
        userId.setBounds(centerX / 2 - 100 / 2, 420, 100, 25);

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

        // Transfer Panel
        idField.setBounds(centerX / 2 - 200 / 2, 155, 200, 25);
        sendFunds.setBounds(centerX / 2 - 100 / 2, 200, 100, 50);
        userIdLabel.setBounds(centerX / 2 - 100 / 2 - 50, 120, 100, 50);
        transfer.setBounds(0, 0, centerX, centerY);
        transfer.setBackground(Color.white);
        transfer.setLayout(null);
        transfer.setVisible(true);

        transfer.add(sendFunds);
        transfer.add(userIdLabel);
        transfer.add(idField);

        // GUI Style
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        warnings.setFont(new Font("Serif", Font.PLAIN, 12));
        warnings.setForeground(Color.RED);
        incorrectJLabel.setForeground(Color.RED);
        incorrectJLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        userId.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(83, 211, 209));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.setBackground(new Color(83, 211, 209));
        registerButton.setBorder(BorderFactory.createEmptyBorder());
        showPass.setBorder(BorderFactory.createEmptyBorder());
        showPass.setForeground(new Color(0, 0, 0));
        showPass.setBackground(new Color(255, 255, 255));
        showPass.setFont(new Font("Serif", Font.PLAIN, 12));
        deposit.setFont(new Font("Arial", Font.BOLD, 15));
        deposit.setForeground(new Color(255, 255, 255));
        deposit.setBackground(new Color(83, 211, 209));
        deposit.setBorder(BorderFactory.createEmptyBorder());
        withdraw.setFont(new Font("Arial", Font.BOLD, 15));
        withdraw.setForeground(new Color(255, 255, 255));
        withdraw.setBackground(new Color(83, 211, 209));
        withdraw.setBorder(BorderFactory.createEmptyBorder());
        send.setFont(new Font("Arial", Font.BOLD, 15));
        send.setForeground(new Color(255, 255, 255));
        send.setBackground(new Color(83, 211, 209));
        send.setBorder(BorderFactory.createEmptyBorder());
        logout.setFont(new Font("Arial", Font.BOLD, 15));
        logout.setForeground(new Color(255, 255, 255));
        logout.setBackground(new Color(83, 211, 209));
        logout.setBorder(BorderFactory.createEmptyBorder());
        sendFunds.setFont(new Font("Arial", Font.BOLD, 15));
        sendFunds.setForeground(new Color(255, 255, 255));
        sendFunds.setBackground(new Color(83, 211, 209));
        sendFunds.setBorder(BorderFactory.createEmptyBorder());

        // Main Frame
        window.setSize(centerX, centerY);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.add(login);
        window.getRootPane().setDefaultButton(loginButton);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button event listeners
        showPass.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1){
                    passField.setEchoChar((char)0);
                }
                else{
                    passField.setEchoChar('•');
                }
            }
        });
        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (Security.updateIndex(userField.getText()) == false) {
                    // Writes the data in user and password fields to their respective files.
                    char[] pass = passField.getPassword();
                    try {
                        filePass.write(userField.getText()+";"+Security.encode(String.valueOf(pass)), fileUser.userInfo);
                    } catch (NoSuchAlgorithmException e1) {
                        e1.printStackTrace();
                    }
                    incorrectJLabel.setText("Successfully Registered");
                    // Updates the userindex
                    Security.updateIndex(userField.getText());
                } else {
                    incorrectJLabel.setText("User already exists!");
                }
            }
        });
        // fix default button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Reads the list of usernames and passwords to be compared by Security.password();
                char[] pass = passField.getPassword();
                try {
                    Security.password(userField.getText(), Security.encode(String.valueOf(pass)));
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
                //Checks for valid input
                if(incorrect==true){
                    incorrectJLabel.setText("Incorrect username or password!");
                }
                //Validates if user is logged in before updating the window to show banking information
                else if(isLoggedIn==true){
                    global.balance = "./balances/balance"+Security.userIndex+".txt";
                    userField.setText("");
                    passField.setText("");
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
                        balanceLabel.setText("Balance: $"+global.balanceGlobal[0]); 
                        userId.setText("User ID: "+Security.userIndex);
                        window.repaint();
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
                UpdateStats.updateBalance(Security.userIndex, "Deposit");
            }
        });
        withdraw.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                UpdateStats.updateBalance(Security.userIndex, "Withdraw");
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
                    if(Integer.parseInt(idField.getText()) != Security.userIndex){
                        int id = Integer.parseInt(idField.getText());
                        warnings.setText("");
                        UpdateStats.updateBalance(id, "Transfer");
                    }
                    else{
                        warnings.setText("Error: You cannot transfer money to yourself.");
                    }
                }
                catch(NumberFormatException n){
                    if(idField.getText().isBlank()){
                        warnings.setText("Error: Please enter user ID to be transferred to.");
                    }
                }
                catch(ArrayIndexOutOfBoundsException a){
                    warnings.setText("Error: User does not exist, please enter a valid ID.");
                }
            }
        });
    }
}
