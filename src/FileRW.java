package DigitalPaymentServices;
import java.io.*;
import java.util.*;

public class FileRW implements readWrite{
    //Public variables(Accessed from each object)
    public List<String> outputList = new ArrayList<String>();
    public List<String> tempList = new ArrayList<String>();
    public String[] output = outputList.toArray(new String[0]);
    public String[][] info;
    public int i = 0;
    public String userInfo = "./credentials.txt";
    public String balance = "./balances/balance"+Security.userIndex+".txt";
   
    // Writes a specified string to a file from a determined path
    public void write(String x, String path) {
        try {
            //New FileWriter object with path and append option set to TRUE in the constructor
            FileWriter write = new FileWriter(path, true);
            //Writes line and new line after each line
            write.write(x + System.getProperty("line.separator"));
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Reads file from a path
    public void read(String path) throws Exception{
        //First clears the arraylist so it does not interfere with reading new file
        outputList.clear();
        //New BufferedReader with path to file in constructor
        BufferedReader reader = new BufferedReader(new FileReader(path));
        //Temporary string to store each read line
        String temp;
        //While reader still has next line, keep looping
        while((temp = reader.readLine()) != null){
            //Adds a new element to the arraylist containing temp(which has read the line in the file)
            outputList.add(temp);
        }
        //ConvertoutputList arraylist to output string array
        output = outputList.toArray(new String[0]);
        reader.close();
    } 
    public void readInfo(String path){
        try{
            //Clears tempList arraylist
            tempList.clear();
            //New BufferedReader with path in constructor
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String temp; // Temp string to store each line
            //While reader has next line, keep looping 
            while((temp = reader.readLine()) != null){
                tempList.add(temp); // add to temp arraylist
            }
            info = new String[tempList.size()][tempList.size()]; // Creates 2d array with size of tempList arraylist
            // Iterates the size of templist
            for(int j = 0; j<tempList.size(); j++){
                //Adds to info arraylist [userIndex][password] by splitting ";"
                info[j] = tempList.get(j).split(";");
            }
            reader.close();
        } catch (Exception e) {
        }
    }
}
class BalanceRW extends FileRW{
    //New arraylist for balance
    public List<String> balanceList = new ArrayList<String>();
    public String[] balanceGlobal = balanceList.toArray(new String[0]);
    //Methods that are required because of the interface inherited from FileRW.
    public void write(String bal, String path){
        try{
            //Writes balances to file
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
            //Clears arraylist
            balanceList.clear();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            //Temporary string
            String temp;
            //While reader still has next line, keep looping
            while((temp = reader.readLine()) != null){
                balanceList.add(temp); //Add temp to balanceList
            }
            //Conert balanceList to balanceGlobal string array
            balanceGlobal = balanceList.toArray(new String[0]);
            reader.close();
        }
        catch(Exception e){
        }
    }
}