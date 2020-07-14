package com.irproject;

import java.io.IOException;
import java.util.ArrayList;

public class Tokenizer extends org.apache.lucene.analysis.Tokenizer {

    private static ArrayList<String> tokens = new ArrayList<String>();
    /**
    * @param tweetText: text of the tweet file to be tokenized
    * @return: an array containing the text splitted into tokens
    */
    public static ArrayList<String> tokenizeTweet(String tweetText){

        tokens = new ArrayList<String>();
        //remove leading and trailing white spaces first
        tweetText = tweetText.trim();

        // remove punctuations
        tweetText = tweetText.replaceAll("[^a-zA-Z#@ ]", "").toLowerCase();
        //split string into tokens
        String[] tokenStrings =  tweetText.split("\\s+");

        for (String token : tokenStrings) {
            if(token.length()> 0) {
                tokens.add(token);
            }
        }


        return tokens;

    }

    @Override
    public boolean incrementToken() throws IOException {
        return false;
    }
}
