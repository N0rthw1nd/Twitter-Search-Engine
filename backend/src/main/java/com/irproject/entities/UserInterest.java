package com.irproject.entities;

import com.irproject.CostumStopList;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.*;
import java.util.ArrayList;

public class UserInterest {

    private String topicName;
    private String categoryName;
    private ArrayList<String> topicBagOfWords;

    /**
     * @param topicName
     * @param categoryName
     */
    public UserInterest(String topicName, String categoryName){
        this.topicName = topicName;
        this.categoryName = categoryName;
        this.topicBagOfWords = new ArrayList<String>();

    }

    /**
     *
     */
    public UserInterest(){
        this.topicName = "";
        this.categoryName = "";
        this.topicBagOfWords = new ArrayList<String>();
    }

    /**
     * Create the bag of words that defines the Topical interest, reading it from a file.
     * @param bowFile  file containing the bag of words for the given topic
     * @throws FileNotFoundException if the given file with the bag of words is not present
     */
    public ArrayList<String> bagOfWordsFromFile(File bowFile) throws FileNotFoundException {

        StandardAnalyzer engAnalyzer = new StandardAnalyzer(CostumStopList.getCostumStopList());
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(bowFile));
            String line = null;
            while( (line = buffer.readLine())!= null) {
                line = line.replaceAll("[^a-zA-Z#@'\\- ]", "");
                TokenStream ts = engAnalyzer.tokenStream("context", new StringReader(line));
                OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
                try {
                    ts.reset(); // Resets this stream to the beginning. (Required)
                    while (ts.incrementToken()) {
                          // Use AttributeSource.reflectAsString(boolean)
                          // for token stream debugging.
                          //System.out.println("token: " + ts.reflectAsString(true));
                            topicBagOfWords.add(ts.getAttribute(CharTermAttribute.class).toString());
                          //topicBagOfWords.add(line.substring(offsetAtt.startOffset(),offsetAtt.endOffset()));
                    }
                    ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
                    } finally {
                      ts.close(); // Release resources associated with this stream.
                    }}

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(bowFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return topicBagOfWords;
    }

    /**
     * @return
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @return
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * @param categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return
     */
    public ArrayList<String> getBagOfWords() {
        return topicBagOfWords;
    }

    /**
     * @param topicBagOfWords
     */
    public void setBagOfWords(ArrayList<String> topicBagOfWords) {
        this.topicBagOfWords = topicBagOfWords;
    }
}
