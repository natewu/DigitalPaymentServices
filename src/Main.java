package DigitalPaymentServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //Creates new GUI named window
        GUI window = new GUI();
        window.frame(); //Calls frame method which opens the GUI window
    }
}

// Security class manages user login verification and user indexing.
class Security {
    public static int userIndex = 0;

    //Updates user index by searching for the login username
    public static boolean updateIndex(String login) {
        // Reads userinfo file which contains usernames and hashed passwords
        GUI.fileUser.readInfo(GUI.fileUser.userInfo); 
        try {
            //Updates userindex using for loop, searches the info 2d array, user index is updated.
            for (userIndex = 0; !login.equals(GUI.fileUser.info[userIndex][0]); userIndex++);
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            GUI.incorrect = true;
            return false;
        }
    }

    //Compares password to the username, passwords are hashed then compared to their hashes stored in file for security. Instead of comparing plain text which could be easy for attackers to compromise.
    public static void password(String login, String pass) {
        //Reads userinfo file which contains usernames and hashed passwords
        GUI.fileUser.readInfo(GUI.fileUser.userInfo);
        try {
            //Updates index to have the latest userIndex
            updateIndex(login);
            //Compares the hashed password stored in memory to the hashed password retrieved from the file and stored in info[userIndex][1], userIndex is the username index and [1] is the password.
            if (pass.equals(GUI.fileUser.info[userIndex][1])) {
                GUI.isLoggedIn = true;
                GUI.incorrect = false;
            } else {
                GUI.incorrect = true;
            }
            //If user does not exist, throws ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException n) {
            updateIndex(login);
            GUI.incorrect = true;
        }
    }

    public static String encode(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        byte[] digest = md.digest();
        StringBuffer hash = new StringBuffer();

        for (byte i : digest) {
            hash.append(String.format("%x", i));
        }
        return hash.toString();
    }
}

// UpdateStats class manages balances and updates the displayed balance.
class UpdateStats extends GUI {
    static void refreshBalance() {
        try {
            FileRW updateBal = new FileRW();
            updateBal.read(GUI.global.balance);
            // System.out.println("Balance: "+GUI.global.balanceGlobal[0]);
            GUI.global.balanceGlobal[0] = updateBal.output[0];
        } catch (Exception e) {
        }
    }

    // Function to update balance
    static void updateBalance(int id, String type) {
        try {
            // Constructor creates new object so the previous data is always cleared.
            BalanceRW transferRW = new BalanceRW();
            transferRW.read("./balances/balance" + id + ".txt");
            // Switch statement allows to choose what should be performed
            switch (type) {
                case "Withdraw", "Transfer":
                    // Checks if user has enough balance before being withdrawn or transferred to
                    // another user and if the amoount is greater than 0.
                    if (Double.parseDouble(amountField.getText()) > 0 && Double.parseDouble(amountField.getText()) - Double.parseDouble(global.balanceGlobal[0]) <= 0) {
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.write(tempBal, "./balances/balance" + id + ".txt");
                        String tempTransfer = Double.toString(Double.parseDouble(global.balanceGlobal[0]) - Double.parseDouble(amountField.getText()));
                        global.write(tempTransfer, global.balance);
                        UpdateStats.refreshBalance();
                        balanceLabel.setText("Balance: $" + tempTransfer);
                        transferRW = null; // deletes data
                        warnings.setText("");
                    } 
                    else if (Double.parseDouble(amountField.getText())
                            - Double.parseDouble(global.balanceGlobal[0]) <= 0) {
                        warnings.setText("Error: " + type + "s must be greater than $0.");
                    } 
                    else {
                        warnings.setText("Error: " + type + "s greater than available funds.");
                    }
                    break;
                case "Deposit":
                    // Check if the amount deposited is greater than 0.
                    if (Double.parseDouble(amountField.getText()) > 0) {
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0])
                                + Double.parseDouble(amountField.getText()));
                        global.write(tempBal, global.balance);
                        UpdateStats.refreshBalance();
                        balanceLabel.setText("Balance: $" + tempBal);
                        transferRW = null; // deletes data
                        warnings.setText("");
                    } else {
                        warnings.setText("Error: " + type + "s must be greater than $0.");
                    }
                    break;
            }
        }
        // Displays error if user enters something other than a numerical value.
        catch (NumberFormatException n) {
            warnings.setText("Error: " + type + "s must a numerical value.");
        }
        // Refreshes window to display updated information
        window.repaint();
    }
    //Converts currencies using free.curr.conv.com API, baseCurrency is for the native currency convertCurrency is for the currency that the native currency is being converted to.
    static double convert(String baseCurrency, String convertCurrency) throws IOException{
        URL url = new URL("https://free.currconv.com/api/v7/convert?q="+baseCurrency+"_"+convertCurrency+"&compact=ultra&apiKey=b5fbfe9903d42cc569ba");
        BufferedReader results = new BufferedReader(new InputStreamReader(url.openStream()));
        String temp[] = results.readLine().split(":");
        for(int i = 0; i < temp.length; i++){
            if(temp[i].contains("{")){
                temp[i]= temp[i].substring(1, temp[i].length());
            }
            else if(temp[i].contains("}")){
                temp[i]= temp[i].substring(0, temp[i].length()-1);
            }
        }
        return Double.parseDouble(temp[1]);
    }
    //Retrieves ISO4217 currency codes from github user content
    static void getCurrencies() throws IOException{
        URL ISO4217 = new URL("https://gist.githubusercontent.com/netdesignr/5d55df291420117de1478314c4756675/raw/9b88efbd4d251115ed5e5e32f8c04bdc6180582f/currency_list_of_the_world_plain.text");
        BufferedReader results = new BufferedReader(new InputStreamReader(ISO4217.openStream()));
        String temp;
        List<String> currencies = new ArrayList<String>();
        while((temp = results.readLine()) != null){
            currencies.add(temp);
        }
        for(int i = 0; i < currencies.size(); i++){
            nativeCurrency.addItem(currencies.get(i));
            conversionCurrency.addItem(currencies.get(i));
        }
    }
}

