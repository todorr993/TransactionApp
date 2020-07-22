package com.company;

import java.util.Set;

public abstract class OutputFile {

    abstract boolean createTables(Set<String> users);
    abstract boolean insertData(String tableName, String receiver, double amount, double saldo);
    abstract void closeConnection();

}
