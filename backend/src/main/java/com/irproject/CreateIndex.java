package com.irproject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import twitter4j.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;


public class CreateIndex {


    private TweetParser tweetParser = new TweetParser();
    //  Use a standard analyzer for indexing
    private StandardAnalyzer analyzer = new StandardAnalyzer(CostumStopList.getCostumStopList());
    // To store an index on disk: (we need the index stored so we can explore it afterwards)
    private Path path = Paths.get("./indexes/");

    // configuration for the index writer
    private IndexWriterConfig config = new IndexWriterConfig(analyzer);

    /**
     * @param lineJson: a line from a tweet file in json format, with 6 fields: text, "text", "accountName", "date",
     *                  "geoLocation", "retweetCount", "favoritesCount"
     * @return: a Document to be indexed        result.add(tweetText);
     */
    private Document createDocument(String lineJson) {

        Document tweetDoc = new Document();
        // define FIELDS for the document corresponding to a tweet, to be indexed.
        Field account = new StringField("account", "", TextField.Store.YES);
        Field tweetText = new TextField("text", "", TextField.Store.YES);
        Field numberOfWords = new IntPoint("numberOfWords", 0);// , Field.Store.YES);
        Field storedNumberOfWords = new StoredField("numberOfWords", 0);
        Field retweetCount = new IntPoint("retweetCount", 0);
        Field storedRetweetCount = new StoredField("retweetCount", 0);
        Field favouriteCount = new IntPoint("favouriteCount", 0);
        Field storedFavouriteCount = new StoredField("favouriteCount", 0);
        Field date = new TextField("date", "", TextField.Store.YES);
// for sorting based on retweets

     //   Field geoLocation = new TextField("date", "", TextField.Store.YES);
        //   Field userMentions = new TextField("userMentions", "", StringField.Store.YES);

        // define list of tweets for the given file
      //  System.out.println(lineJson);
        //lineJson = lineJson.replace("\"@", "@");

        JSONObject jsonObject = new JSONObject(lineJson);

        Map<String, ArrayList<String>> singleTweetData = tweetParser.parse(jsonObject.getString("text"));

        int wordsCount = singleTweetData.get("tokens").size();
        account.setStringValue((String) jsonObject.get("accountName"));
        tweetText.setStringValue(singleTweetData.get("text").get(0));
        numberOfWords.setIntValue(Integer.valueOf(wordsCount));
        storedNumberOfWords.setIntValue(Integer.valueOf(wordsCount));
        retweetCount.setIntValue((Integer) jsonObject.get("retweetCount"));
        storedRetweetCount.setIntValue((Integer) jsonObject.get("retweetCount"));
        favouriteCount.setIntValue((Integer) jsonObject.get("favoritesCount"));
        storedFavouriteCount.setIntValue((Integer) jsonObject.get("favoritesCount"));
        date.setStringValue((String) jsonObject.get("date"));
    //    geoLocation.setStringValue((String) jsonObject.get("geoLocation"));

        for (String hash : singleTweetData.get("hashtag") ) {
            Field hashtags = new TextField("hashtag", hash, TextField.Store.YES);
            tweetDoc.add(hashtags);
        }
        tweetDoc.add(account);
        tweetDoc.add(tweetText);
        tweetDoc.add(numberOfWords);
        tweetDoc.add(storedNumberOfWords);
        tweetDoc.add(retweetCount);
        tweetDoc.add(storedRetweetCount);
        tweetDoc.add(new NumericDocValuesField("retweet", (Integer) jsonObject.get("retweetCount")));
        tweetDoc.add(favouriteCount);
        tweetDoc.add(storedFavouriteCount);
        tweetDoc.add(new NumericDocValuesField("favourites", (Integer) jsonObject.get("favoritesCount")));
        tweetDoc.add(date);
    //    tweetDoc.add(geoLocation);

        return tweetDoc;
    }


    //VERY important to use the same analyzer to create the index, to read, search and parse the index afterwards!

    /**
     * @return: the index writer for al the collected tweets
     */
    public IndexWriter createIndex() throws IOException {

 //       System.out.println("apertura directory");
        // define index writer for writing the index
        Directory directory = FSDirectory.open(path);
        IndexWriter iwriter = new IndexWriter(directory, config);
     //   System.out.println("directory aperta");
        // define similarity Measure
        Similarity similarityMeasure = new ClassicSimilarity();  // try ClassicSimilarity BM25Similarity
  //      Similarity similarityMeasure = new ClassicSimilarity();

        // define list of categories to be crawled
        String[] categoryList = {"tech", "sport", "food", "news", "music", "cinema"};

        ArrayList<Document> listOfDocuments = new ArrayList<Document>();
        // for each category read saved tweets and store them into index
        for (String category : categoryList) {
            File tweetsCategory = new File("tweetsData/" + category);
            String[] files = tweetsCategory.list();
            // given a category read every file in its directory
            for (String filename : files) {
                System.out.println(filename);
                ArrayList<String> lines = new ArrayList<String>();
                try {
                    // open input stream test.txt for reading purpose.
                    File tweetsFile = new File("tweetsData/" + category + "/" + filename);
                    BufferedReader reader = new BufferedReader(new FileReader(tweetsFile));

                    String tweetLine = null;
                    while ((tweetLine = reader.readLine()) != null) {
                        lines.add(tweetLine);

                    }
                } catch (FileNotFoundException e) {
                    throw new FileNotFoundException("Error: " + filename + " not Exist");
                }

                // ArrayList<String> users = new ArrayList<String>();
                // for each line in file create a document to store tweet text and metadata
                for (String line : lines) {

                    Document tweet = createDocument(line); // read all tweets from file

                    listOfDocuments.add(tweet);
                    // save created document into index writer
                    iwriter.addDocument(tweet);

                }
            }


        }
        iwriter.close();
        return iwriter;
    }



}
