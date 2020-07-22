package com.company;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class InputFileFactory {

    public InputFile factoryMethod(String typeOfFile, String path){
        InputFile inputFile=null;
        if (typeOfFile.equalsIgnoreCase("excel"))
        try {
            inputFile=new InputExcelFile(path);
        }catch (IOException | InvalidFormatException e)
    {
        System.out.println("Input File Factory: Input Excel File Exception");
        //e.printStackTrace();
    }

        return inputFile;
    }//end method

}
