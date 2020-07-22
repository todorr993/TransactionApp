package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerTransaction {
    PrintWriter printWriter;
    TransactionManager transactionManager;
    String typeOfInputFile;
    String pathToInputFile;
    String typeOfOutputFile;
    String pathToDataBase;
    int port;

    public ServerTransaction(String typeOfInputFile, String pathToInputFile, String typeOfOutputFile, String pathToDataBase, int port)
    {
        this.typeOfInputFile=typeOfInputFile;
        this.pathToInputFile=pathToInputFile;
        this.typeOfOutputFile=typeOfOutputFile;
        this.pathToDataBase=pathToDataBase;
        this.port=port;
    }

    public void go()
    {
        //clients=new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            transactionManager = new TransactionManager();
            transactionManager.loadData(typeOfInputFile, pathToInputFile);
            //transactionManager.loadData("excel", "C:\\Users\\todorr\\Desktop\\Backend task\\Level2\\ExternalInputExample.xlsx");
            System.out.println("Waiting for client...");
            while (true)
            {
                Socket clientSocket= serverSocket.accept();
                printWriter=new PrintWriter(clientSocket.getOutputStream());
                System.out.println("Connection is established.");

                //clients.add(printWriter);
                printWriter.println("Hello, to do transaction, please write in next format: Sender Receiver Amount");
                printWriter.flush();

                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket));
                clientHandlerThread.start();
            }

        }catch (IOException e)
        {
            System.out.println("ServerTransaction: Error while creating Server ");
            //e.printStackTrace();
        }
    }//end method go()



    //additional class to handle client request
    public class ClientHandler implements  Runnable
    {
        Socket socket;
        BufferedReader bufferedReader;


        public ClientHandler (Socket socket)
        {
            try {
                this.socket = socket;
                InputStreamReader inputStream = new InputStreamReader(this.socket.getInputStream());
                bufferedReader = new BufferedReader(inputStream);
            }catch (IOException e)
            {
                System.out.println("SeverTransaction: Error while connecting to Client ");
                //e.printStackTrace();
            }
        }//close constructor

        @Override
        public void run() {
            String message;
            try {
                while ((message = bufferedReader.readLine()) != null)
                {
                    System.out.println("Client transaction:");
                    System.out.println(message);
                    String[] array=message.split(" ");
                    //if transaction is successful, close the program.
                    if (transactionManager.transferRequest(array[0], array[1], Double.valueOf(array[2]))) {
                        printWriter.println("Transaction is done! System closed.");
                        printWriter.flush();
                        transactionManager.insertData(typeOfOutputFile, pathToDataBase);
                        //transactionManager.insertData("SQLite", "C:\\Users\\todorr\\Desktop\\Backend task\\Level2\\OutputExample2.sqlite3");
                        printWriter.close();
                        bufferedReader.close();
                        socket.close();
                        System.exit(0);
                    }else {
                        printWriter.println("Transfer request is rejected. Check your transaction data and format!");
                        printWriter.flush();
                    }
                }
            }catch (IOException e)
            {
                System.out.println("ServerTransaction/ClientHandler: Error while reading from Clients");
                System.exit(0);
                //e.printStackTrace();
            }
        }
    }//close inner class

}
