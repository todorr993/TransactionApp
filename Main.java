package com.company;

public class Main {

    public static void main(String[] args) {
        String inputFile;
        String outputFile;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Please insert input path file");
        inputFile=scanner.nextLine();
        //"C:\\Users\\todorr\\Desktop\\Backend task\\Level2\\ExternalInputExample.xlsx"
        
        System.out.println("Please insert output path file");
        outputFile=scanner.nextLine();
        
        //"C:\\Users\\todorr\\Desktop\\Backend task\\Level2\\OutputExample2.sqlite3"
        new ServerTransaction("excel",inputFile, "sqlite", outputFile, 5000).go();
        System.exit(0);
        sc.close();

    }
}
