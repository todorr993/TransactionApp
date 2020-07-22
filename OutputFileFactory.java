package com.company;

public class OutputFileFactory {


    public OutputFile factoryMethod(String typeOfFile, String pathToOutputFile)
    {
        OutputFile outputFile=null;

        if(typeOfFile.equalsIgnoreCase("SQLite"))
            outputFile=new OutputFileSQLite(pathToOutputFile);

        return outputFile;
    }//end method

}
