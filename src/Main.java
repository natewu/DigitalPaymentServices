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
        //New MessageDigest object using SHA-256 hashing
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        byte[] digest = md.digest();
        StringBuffer hash = new StringBuffer();
        //Alg for hashing the data
        for (byte i : digest) {
            hash.append(String.format("%x", i));
        }
        //Returns hashed password
        return hash.toString();
    }
}

// UpdateStats class manages balances and updates the displayed balance.
class UpdateStats extends GUI {
    static void refreshBalance() {
        try {
            //refreshes balance by creating a new FileRW object(clears data) and setting global balance to the updated output
            FileRW updateBal = new FileRW();
            updateBal.read(GUI.global.balance);
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
                        //Temporary balance to add amount to withdraw/transfer amount
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.write(tempBal, "./balances/balance" + id + ".txt"); // Write to balance file
                        //Subtract funds
                        String tempTransfer = Double.toString(Double.parseDouble(global.balanceGlobal[0]) - Double.parseDouble(amountField.getText()));
                        global.write(tempTransfer, global.balance);
                        UpdateStats.refreshBalance(); // Update balance
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
                        //Temporary balance to add amount to deposit amount
                        String tempBal = Double.toString(Double.parseDouble(transferRW.balanceGlobal[0]) + Double.parseDouble(amountField.getText()));
                        global.write(tempBal, global.balance); // Writes to balance file
                        UpdateStats.refreshBalance(); //Update balancec
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
        //Create URL object that contains API information and API key
        URL url = new URL("https://free.currconv.com/api/v7/convert?q="+baseCurrency+"_"+convertCurrency+"&compact=ultra&apiKey=b5fbfe9903d42cc569ba");
        //Opens a connection to the API
        BufferedReader results = new BufferedReader(new InputStreamReader(url.openStream()));
        //Temporary string to store values in an array
        String temp[] = results.readLine().split(":");
        //Removes brackets from output
        for(int i = 0; i < temp.length; i++){
            if(temp[i].contains("{")){
                temp[i]= temp[i].substring(1, temp[i].length());
            }
            else if(temp[i].contains("}")){
                temp[i]= temp[i].substring(0, temp[i].length()-1);
            }
        }
        //Returns conversion rate retrieved from the API stored in temp array index 1(index 0 is conversion information)
        return Double.parseDouble(temp[1]);
    }
    //Retrieves ISO4217 currency codes from github user content
    static void getCurrencies() throws IOException{
        //Creates URL object that contains information from github
        URL ISO4217 = new URL("https://gist.githubusercontent.com/netdesignr/5d55df291420117de1478314c4756675/raw/9b88efbd4d251115ed5e5e32f8c04bdc6180582f/currency_list_of_the_world_plain.text");
        //Opens connection to the URL
        BufferedReader results = new BufferedReader(new InputStreamReader(ISO4217.openStream()));
        //Temporary string to store each line(each currency code is on new line)
        String temp;
        //Creates arraylist to dynamically add currency codes to the array
        List<String> currencies = new ArrayList<String>();
        while((temp = results.readLine()) != null){
            currencies.add(temp);
        }
        //Retrieves information from arraylist(iterates the size of arraylist) and adds to JComboBox list
        for(int i = 0; i < currencies.size(); i++){
            nativeCurrency.addItem(currencies.get(i));
            conversionCurrency.addItem(currencies.get(i));
        }
    }
}

