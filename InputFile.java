package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public abstract class InputFile  {

    abstract  HashMap<String, LinkedList<Transaction>> loadData() ;

}
