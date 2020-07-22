package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class OutputFileSQLite extends OutputFile{
    private Connection connection;
    private Statement statement;

    public OutputFileSQLite (String pathToOutputFile)
    {

        // db parameters
         String url = "jdbc:sqlite:"+pathToOutputFile;
            // create a connection to the database
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement=connection.createStatement();
        }catch (SQLException|ClassNotFoundException e)
        {
            System.out.println("Output SQLite Exception: Connection problem.");
        }
    }

    boolean createTables(Set<String> users)
    {
        boolean success=false;
        try {
            int i=1;
            dropTable("Balance");
            createBalanceTable();
            for (String user : users) {
                insertBalanceData(i, user, 0);
                dropTable(user);
                createAccountTable(user);
                i++;
            }
            return !success;
        }catch (SQLException e)
        {
            System.out.println("Output SQLite Exception: Tables are not created.");
            e.printStackTrace();
            return success;
        }
    }
    public boolean insertData(String tableName, String receiver, double amount, double saldo){
        boolean success=false;
        try {
            insertAccountData(tableName, receiver, amount);
            updateBalanceData(tableName, saldo);
            return !success;
        }catch (SQLException e)
        {
            System.out.println("Output SQLite Exception: Data is not inserted");
            //e.printStackTrace();
            return success;
        }
    }

    public void closeConnection()
    {
        try {
            statement.close();
            connection.close();
        }catch (SQLException e)
        {
            System.out.println("Output SQLite Exception: Connection can not be closed");
            //e.printStackTrace();
        }
    }

    private void dropTable (String tableName) throws SQLException
    {
        String sql="DROP TABLE IF EXISTS "+tableName;
        statement.execute(sql);
    }
    private void createAccountTable (String tableName) throws SQLException
    {
        String sql="CREATE TABLE "+ tableName.toUpperCase()+

                    " (RECEIVER CHAR(50) NOT NULL," +
                    " AMOUNT DOUBLE NOT NULL)";
        statement.execute(sql);
    }

    private void insertAccountData(String tableName, String receiver, double amount) throws SQLException
    {
        String sql="INSERT INTO "+ tableName.toUpperCase()+ " (RECEIVER, AMOUNT)"+
                "VALUES ( '"+ receiver +"', "+ amount+");";

        statement.execute(sql);
    }
    private void createBalanceTable () throws SQLException
    {
        String sql="CREATE TABLE  BALANCE"+
                " (ID INT PRIMARY KEY NOT NULL," +
                " SENDER CHAR(50) NOT NULL," +
                " AMOUNT DOUBLE NOT NULL);";
        statement.execute(sql);
    }

    private void insertBalanceData(int id, String sender, double amount) throws SQLException
    {
        String sql="INSERT INTO BALANCE (ID, SENDER, AMOUNT)"+
                "VALUES (" +id +", '"+ sender +"', "+ amount+");";

        statement.execute(sql);
    }

    private void updateBalanceData(String sender, double amount) throws SQLException
    {
        String sql="UPDATE BALANCE SET AMOUNT = "+amount+" WHERE SENDER = '"+sender+"';";
        statement.execute(sql);
    }



}
