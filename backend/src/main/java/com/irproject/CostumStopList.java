package com.irproject;

import org.apache.lucene.analysis.CharArraySet;

import java.io.*;
import java.util.ArrayList;

public class CostumStopList {

    private static CharArraySet stopList = new CharArraySet(15, true);
    private static String filepath = "./StopList/StopList.txt";

    public CostumStopList() throws FileNotFoundException {
        setFileCostumStopList(filepath);
    }


    /**
     * @param filepath the path of the file with the stopwords
     * @throws FileNotFoundException
     */
    private static void setFileCostumStopList(String filepath) throws FileNotFoundException {

        File stopFile = new File(filepath);
        try {

            BufferedReader reader = new BufferedReader(new FileReader(stopFile));

            String stopword = null;
            while ((stopword = reader.readLine()) != null) {
                stopword = stopword.replace("\n", "");
                stopList.add(stopword);

            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Error: " + filepath + " not Found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static  CharArraySet getCostumStopList(){
        return stopList;
    }




}
