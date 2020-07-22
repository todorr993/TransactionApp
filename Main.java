package com.company;

public class Main {

    public static void main(String[] args) {

        new ServerTransaction("excel","C:\\Users\\todorr\\Desktop\\Backend task\\Level2\\ExternalInputExample.xlsx", "sqlite", "C:\\Users\\todorr\\Desktop\\Backend task\\Level2\\OutputExample2.sqlite3", 5000).go();
        System.exit(0);

    }
}
