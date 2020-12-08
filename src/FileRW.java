package DigitalPaymentServices;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class FileRW implements readWrite{
    //Public variables(Accessed from each object)
    public List<String> outputList = new ArrayList<String>();
    public String[] output = outputList.toArray(new String[0]);
    public int i = 0;
    public String userList = "./credentials.txt";
    public String passList = "./passlist.txt";
    public String balance = "./balances/balance"+Security.userIndex+".txt";
   
    // Writes a specified string to a file from a determined path
    public void write(String x, String path) {
        try {
            FileWriter write = new FileWriter(path, true);
            write.write(x + System.getProperty("line.separator"));
            write.close();
        } catch (IOException e) {
        }
    }
    
    // Reads file from a path
    public void read(String path) {
        try {
            outputList.clear();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String temp;
            while((temp = reader.readLine()) != null){
                outputList.add(temp);
            }
            output = outputList.toArray(new String[0]);
            reader.close();
        } catch (Exception e) {
        }
    }
}
class BalanceRW extends FileRW implements readWrite{
    public List<String> balanceList = new ArrayList<String>();
    public String[] balanceGlobal = balanceList.toArray(new String[0]);
    public void write(String bal, String path){
        try{
            FileWriter write = new FileWriter(path, false);
            write.write(bal + System.getProperty("line.separator"));
            write.flush();
            write.close();
        }
        catch(IOException e){
        }
    }
    public void read(String path){
        try{
            balanceList.clear();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String temp;
            while((temp = reader.readLine()) != null){
                balanceList.add(temp);
            }
            balanceGlobal = balanceList.toArray(new String[0]);
            reader.close();
        }
        catch(Exception e){
        }
    }
}