package DigitalPaymentServices;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.*;
import java.awt.*;

//Defines all the variables and creates objects
class GUIDefinitions {
    // Public variables with static variables that can be accessed globally.
    public static FileRW fileUser = new FileRW();
    public static FileRW filePass = new FileRW();
    public static BalanceRW global = new BalanceRW();
    public String user;
    public String pass;
    public static boolean isLoggedIn = false;
    static boolean active = false;
    static boolean incorrect = false;

    // GUI Frame and panels
    static JFrame window = new JFrame("Digital Payment Services");
    static JPanel login = new JPanel();
    static JPanel banking = new JPanel();
    static JPanel transfer = new JPanel();
    static JPanel curExchange = new JPanel();

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
    static JButton currency = new JButton("Currency Converter");
    static JButton logout = new JButton("Logout");
    static JLabel amount = new JLabel("$");
    static JLabel balanceLabel = new JLabel("Balance: $0");
    static JLabel warnings = new JLabel();
    static JLabel userId = new JLabel("User ID: ");

    // Currency panel elements
    static JButton convert = new JButton("Convert");
    static JLabel info = new JLabel("<html>Conversion rates are updated every 60 minutes and are provided by the currencyconverterapi.com API.</html>");
    static JLabel conversionError = new JLabel("");
    static JTextField exchangeFrom = new JTextField();
    static JTextField exchangeTo = new JTextField();
    static JComboBox<String> nativeCurrency = new JComboBox();
    static JComboBox<String> conversionCurrency = new JComboBox();

    // Transfer panel elements
    static JTextField idField = new JTextField();
    static JButton sendFunds = new JButton("Transfer");
    static JLabel userIdLabel = new JLabel("User ID: ");
}

//GUI class with methods and inherits from GUIDefinitions
public class GUI extends GUIDefinitions {
    void frame() throws IOException {
        // Initialization events
        int centerX = 380;
        int centerY = 500;

        // Login Panel
        username.setBounds(centerX / 2 - 295 / 2, 75, 280, 25);
        incorrectJLabel.setBounds(centerX / 2 - 295 / 2, 50, 280, 25);
        password.setBounds(centerX / 2 - 295 / 2, 130, 280, 25);
        userField.setBounds(centerX / 2 - 295 / 2, 100, 280, 25);
        passField.setBounds(centerX / 2 - 295 / 2, 155, 280, 25);
        showPass.setBounds(centerX / 2 - 295 / 2, 183, 280, 25);
        loginButton.setBounds(centerX / 2 - 145 / 2 - 75, 250, 130, 60);
        registerButton.setBounds(centerX / 2 - 145 / 2 + 75, 250, 130, 60);

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
        deposit.setBounds(centerX / 2 - 120 / 2 + 55, 210, 105, 50);
        withdraw.setBounds(centerX / 2 - 120 / 2 - 55, 210, 105, 50);
        send.setBounds(centerX / 2 - 230 / 2, 270, 215, 50);
        currency.setBounds(centerX / 2 - 230 / 2, 150, 215, 50);
        logout.setBounds(centerX / 2 - 230 / 2, 340, 215, 50);
        amountField.setBounds(centerX / 2 - 225 / 2, 100, 210, 25);
        amount.setBounds(centerX / 2 - 220 / 2 - 25, 100, 100, 25);
        balanceLabel.setBounds(centerX / 2 - 220 / 2, 30, 200, 25);
        warnings.setBounds(centerX / 2 - 320 / 2, 70, 300, 25);
        userId.setBounds(centerX / 2 - 110 / 2, 420, 100, 25);

        banking.setBounds(0, 0, centerX, centerY);
        banking.setBackground(Color.white);
        banking.setLayout(null);
        banking.setVisible(true);
        banking.add(deposit);
        banking.add(withdraw);
        banking.add(send);
        banking.add(currency);
        banking.add(logout);
        banking.add(amountField);
        banking.add(amount);
        banking.add(balanceLabel);
        banking.add(warnings);
        banking.add(userId);

        // Currency Panel
        UpdateStats.getCurrencies();

        convert.setBounds(centerX / 2 - 100 / 2, 105, 100, 25);
        exchangeFrom.setBounds(centerX / 2 - 175, 70, 100, 25);
        exchangeTo.setBounds(centerX / 2 + 8, 70, 100, 25);
        nativeCurrency.setBounds(centerX / 2 - 73, 70, 70, 24);
        conversionCurrency.setBounds(centerX / 2 + 110, 70, 70, 24);
        info.setBounds(centerX / 2 - 300/2, 1, 300, 74);
        conversionError.setBounds(centerX / 2 - 180/2, 130, 180, 24);

        exchangeTo.setEditable(false); // prevent editing of textfield
        //Set default selected items
        nativeCurrency.setSelectedItem("CAD");
        conversionCurrency.setSelectedItem("EUR");

        curExchange.setBackground(Color.white);
        curExchange.setLayout(null);
        curExchange.setVisible(true);
        curExchange.add(info);
        curExchange.add(conversionError);
        curExchange.add(convert);
        curExchange.add(exchangeFrom);
        curExchange.add(exchangeTo);
        curExchange.add(nativeCurrency);
        curExchange.add(conversionCurrency);

        // Transfer Panel
        idField.setBounds(centerX / 2 - 225 / 2, 155, 210, 25);
        sendFunds.setBounds(centerX / 2 - 120 / 2, 210, 100, 50);
        userIdLabel.setBounds(centerX / 2 - 120 / 2 - 50, 120, 100, 50);
        transfer.setBounds(0, 0, centerX, centerY);
        transfer.setBackground(Color.white);
        transfer.setLayout(null);
        transfer.setVisible(true);

        transfer.add(sendFunds);
        transfer.add(userIdLabel);
        transfer.add(idField);

        // GUI Style
        final Color foreground = new Color(255, 255, 255); //variable for colors so its more efficient for cases that involve changing colors.
        final Color background = new Color(83, 211, 209);

        balanceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        warnings.setFont(new Font("Serif", Font.PLAIN, 12));
        warnings.setForeground(Color.RED);
        incorrectJLabel.setForeground(Color.RED);
        incorrectJLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        userId.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setForeground(foreground);
        loginButton.setBackground(background);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        registerButton.setForeground(foreground);
        registerButton.setBackground(background);
        registerButton.setBorder(BorderFactory.createEmptyBorder());
        showPass.setBorder(BorderFactory.createEmptyBorder());
        showPass.setForeground(new Color(0, 0, 0));
        showPass.setBackground(foreground);
        showPass.setFont(new Font("Serif", Font.PLAIN, 12));
        deposit.setFont(new Font("Arial", Font.BOLD, 15));
        deposit.setForeground(foreground);
        deposit.setBackground(background);
        deposit.setBorder(BorderFactory.createEmptyBorder());
        withdraw.setFont(new Font("Arial", Font.BOLD, 15));
        withdraw.setForeground(foreground);
        withdraw.setBackground(background);
        withdraw.setBorder(BorderFactory.createEmptyBorder());
        send.setFont(new Font("Arial", Font.BOLD, 15));
        send.setForeground(foreground);
        send.setBackground(background);
        send.setBorder(BorderFactory.createEmptyBorder());
        currency.setFont(new Font("Arial", Font.BOLD, 15));
        currency.setForeground(foreground);
        currency.setBackground(background);
        currency.setBorder(BorderFactory.createEmptyBorder());
        convert.setFont(new Font("Arial", Font.BOLD, 15));
        convert.setForeground(foreground);
        convert.setBackground(background);
        convert.setBorder(BorderFactory.createEmptyBorder());
        logout.setFont(new Font("Arial", Font.BOLD, 15));
        logout.setForeground(foreground);
        logout.setBackground(background);
        logout.setBorder(BorderFactory.createEmptyBorder());
        sendFunds.setFont(new Font("Arial", Font.BOLD, 15));
        sendFunds.setForeground(foreground);
        sendFunds.setBackground(background);
        sendFunds.setBorder(BorderFactory.createEmptyBorder());

        // Main Frame
        window.setSize(centerX, centerY);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.add(login);
        window.getRootPane().setDefaultButton(loginButton); //Default "enter" key action will map to the log in button
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button event listeners
        showPass.addItemListener(new ItemListener() {
            //Checks the state change of the showPass checkbox
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) { //Selected, shows raw values
                    passField.setEchoChar((char) 0);
                } 
                else { //Not selected, masks values with "•" unicode character
                    passField.setEchoChar('•');
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            //Checks if registerButton has been pressed
            public void actionPerformed(ActionEvent e) {
                //Checks if user already exists or not
                if (Security.updateIndex(userField.getText()) == false) {
                    // Writes the data in user and password fields to their respective files.
                    char[] pass = passField.getPassword();
                    try {
                        //Try to write username with separator ";" then hashed password using SHA-256 hashing algorithm
                        filePass.write(userField.getText() + ";" + Security.encode(String.valueOf(pass)), fileUser.userInfo);
                    } 
                    catch (NoSuchAlgorithmException e1) {
                        //If algorithm is not imported, catch error
                        e1.printStackTrace();
                    }
                    //If registered, print message on GUI
                    incorrectJLabel.setText("Successfully Registered");
                    // Updates the userindex
                    Security.updateIndex(userField.getText());
                } else {
                    //If user already exists, print message on GUI
                    incorrectJLabel.setText("User already exists!");
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Reads the list of usernames and passwords to be compared by
                // Security.password();
                char[] pass = passField.getPassword();
                try {
                    Security.password(userField.getText(), Security.encode(String.valueOf(pass)));
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
                // Checks for valid input
                if (incorrect == true) {
                    incorrectJLabel.setText("Incorrect username or password!");
                }
                // Validates if user is logged in before updating the window to show banking information
                else if (isLoggedIn == true) {
                    global.balance = "./balances/balance" + Security.userIndex + ".txt";
                    userField.setText("");
                    passField.setText("");
                    try {
                        //Refreshes balance and reads balance from file
                        UpdateStats.refreshBalance();
                        global.read(global.balance);
                        //Sets information to be displayed on GUI
                        balanceLabel.setText("Balance: $" + global.balanceGlobal[0]);
                        userId.setText("User ID: " + Security.userIndex);
                    }
                    // If user is newly registered, creates a file to store the user balance
                    catch (ArrayIndexOutOfBoundsException E) {
                        //Writes new balance with $0 in funds
                        global.write("0", global.balance);
                        //Refreshes balance
                        UpdateStats.refreshBalance();
                        //Reads balance and updates information on GUI
                        global.read(global.balance);
                        balanceLabel.setText("Balance: $" + global.balanceGlobal[0]);
                        userId.setText("User ID: " + Security.userIndex);
                        //Refreshes GUI
                        window.repaint();
                    }
                    //Updates GUI
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
        deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Uses updateBalance method and option "Deposit" to write balance to file and update on screen information
                UpdateStats.updateBalance(Security.userIndex, "Deposit");
            }
        });
        withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Uses updateBalance method and option "Withdraw" to write balance to file and update on screen information
                UpdateStats.updateBalance(Security.userIndex, "Withdraw");
            }
        });
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switches between the banking and transfer panel, send(JButton) is a toggle, uses boolean to check toggle state
                if (active == false) {
                    //If clicked, remove banking panel and add transfer panel
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
                    send.setBounds(centerX / 2 - 120 / 2, 270, 100, 50);
                    window.add(transfer);
                    window.validate();
                    active = true;
                } 
                else if (active == true) {
                    //If clicked and already on transfer panel, remove transfer panel and add banking panel
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
                    send.setBounds(centerX / 2 - 230 / 2, 270, 215, 50);
                    window.add(banking);
                    window.validate();
                    active = false;
                }
            }
        });
        currency.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Creates new dialog box popup
                JDialog box = new JDialog();

                //Adds elements to GUI
                box.setSize(400, 200);
                box.setTitle("Currency Conversion");
                box.add(curExchange);
                box.setResizable(false);
                box.setLocationRelativeTo(null);
                box.setVisible(true);
            }
        });
        convert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Number formatter to format double to 2 decimal places(since it is currency)
                NumberFormat format = new DecimalFormat("#0.00");
                try {
                    //Calls API with information from input + comboboxes
                    exchangeTo.setText(String.valueOf(format.format(Double.parseDouble(exchangeFrom.getText()) * UpdateStats.convert(nativeCurrency.getSelectedItem().toString().trim(), conversionCurrency.getSelectedItem().toString().trim()))));
                    //Sets message on screen to display the conversion rate
                    conversionError.setBounds(centerX / 2 - 170/2, 130, 170, 24);
                    conversionError.setText("Rate: 1 "+nativeCurrency.getSelectedItem().toString()+" = "+String.valueOf(format.format(UpdateStats.convert(nativeCurrency.getSelectedItem().toString().trim(), conversionCurrency.getSelectedItem().toString().trim())))+" "+conversionCurrency.getSelectedItem().toString());
                } 
                catch (IOException e1) {
                    e1.printStackTrace();
                }
                catch(NumberFormatException e2){
                    //set warning label
                    conversionError.setBounds(centerX / 2 - 180/2, 130, 180, 24);
                    conversionError.setText("Please input a numerical value.");
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
                window.getRootPane().setDefaultButton(loginButton);
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
