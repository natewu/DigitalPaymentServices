package DigitalPaymentServices;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main{
    public static void main(String[] args) {
        GUI window = new GUI();
        window.frame();
    }
}

// Security class manages user login verification and user indexing.
class Security{
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

    public static String encode(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        byte[] digest = md.digest();
        StringBuffer hash = new StringBuffer();
        
        for(byte i : digest){
            hash.append(String.format("%x", i));
        }
        return hash.toString();
    }
}
//UpdateStats class manages balances and updates the displayed balance.
class UpdateStats extends GUI{
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
    //Function to update balance
    static void updateBalance(int id, String type){
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

