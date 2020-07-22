package com.company;

// load data from input file, do transaction, insert new data set to data base


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TransactionManager {

    private InputFileFactory inputFileFactory;
    private OutputFileFactory outputFileFactory;
    private HashMap<String, LinkedList<Transaction>> users;

    public TransactionManager ()
    {
        users=new HashMap<>();
        inputFileFactory=new InputFileFactory();
        outputFileFactory=new OutputFileFactory();
    }

    //loads data in HashMap users from typeOfFile source on provided Path
    public void loadData(String typeOfFile, String path)
    {
            InputFile inputFile = inputFileFactory.factoryMethod(typeOfFile, path);
            users = inputFile.loadData();
            System.out.println("Successfully loaded data!");

    }//end method


    //transfer Amount from Sender to Receiver if there is enough money
    public boolean transferRequest (String sender, String receiver, double amount)
    {
        boolean success=true;
        //if Sender and Receiver are in User Set
        if (users.containsKey(sender) && users.containsKey(receiver))
        {
            double saldo=users.get(sender).get(users.get(sender).size()-1).getSaldo();
            //if there is enough money on sender Account
            if (saldo>=amount)
            {
                //add transaction to Sender History
                users.get(sender).add(new Transaction(receiver, - amount, saldo - amount));
                //add transaction to Receiver History
                double saldoReceiver = users.get(receiver).get(users.get(receiver).size()-1).getSaldo();
                users.get(receiver).add(new Transaction(sender, amount, saldoReceiver + amount));
                return success;
            }
            else {
                System.out.println(sender+ " does NOT have enough money on the account. Transaction is rejected!");
                return !success;
            }
        }else {
            System.out.println("User " + sender + "/" + receiver + " does NOT exist!");
            return !success;
        }

    }//end method

    //insert data to Output file
    public void insertData (String typeOfFile, String path)
    {
        OutputFile outputFile=outputFileFactory.factoryMethod(typeOfFile, path);

        if (outputFile.createTables(users.keySet()))
        {
            boolean success=true;
            System.out.println("Tables are successfully created.");
            for (String user: users.keySet()) {
                for (Transaction transaction: users.get(user)) {
                    if (!outputFile.insertData(user, transaction.getReceiver(), transaction.getAmount(), transaction.getSaldo()))
                    {
                        success=false;
                        break;
                    }
                }
                if (!success) break;
                }
            System.out.println("Data is transferred.");
            outputFile.closeConnection();
        }


    }//end method




}
