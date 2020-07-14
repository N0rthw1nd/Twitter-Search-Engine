package com.irproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetParser {

    Map<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();

    /**
     * @param tweetText: the text of the tweet to be parsed
     * @return an array that memorizes in the given indexes:
     *          - 0: The url in the tweet if any
     *          - 2 -> n-2 : the list of hashtags being used
     *          - n-1 : the text of the tweet.
     */
    public Map<String, ArrayList<String>> parse(String tweetText) {
        /* result is an array that memorizes in the given indexes:
         * 0: The url in the tweet if any
         * 1 - n-1 : the list of hashtags being used
         */
               // Search for URLs
        String url = "";
        int indexOfHttp = -1;
        // list of urls inside the tweet
        ArrayList<String> listOfUrls = new ArrayList<String>();
        while (tweetText.contains("http:") || tweetText.contains("https:")) {
            if (tweetText.contains("https:")) {
                indexOfHttp = tweetText.indexOf("https:");
            } else {
                indexOfHttp = tweetText.indexOf("http:");
            }
            int endPoint = (tweetText.indexOf(' ', indexOfHttp) != -1) ? tweetText.indexOf(' ', indexOfHttp) : tweetText.length();
            url = tweetText.substring(indexOfHttp, endPoint);
            // String targetUrlHtml=  "<a href='${url}' target='_blank'>${url}</a>";
            //            tweetText = tweetText.replace(url,targetUrlHtml );
            tweetText = tweetText.replace(url, "");
            listOfUrls.add(url);
        }
        this.result.put("urls", listOfUrls);

        // clean text from punctactions
        String patternStr = "(?:\\s|\\A\n)[##]+([A-Za-z0-9-_]+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(tweetText);
        String hashtagFound = "";
        // list of hashtag inside the tweet
        ArrayList<String> listOfHashtags = new ArrayList<String>();

        // Search for Hashtags
        while (matcher.find()) {
            hashtagFound = matcher.group();
            //cleanTweet = cleanTweet.replace(" ", "");
            String removedHashtag = hashtagFound.replace("#", "");
            // String searchHTML="<a href='http://search.twitter.com/search?q=" + search + "'>" + cleanTweet + "</a>"
           // tweetText = tweetText.replace(hashtagFound, removedHashtag);
            listOfHashtags.add(hashtagFound);
        }

        this.result.put("hashtag", listOfHashtags);

        // Search for Users
        patternStr = "(?:\\s|\\A\n)[@]+([A-Za-z0-9-_]+)";
        pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher(tweetText);
        String userFound = "";

        // list of users mentioned inside the tweet
        ArrayList<String> listOfUsersMentions = new ArrayList<String>();

        while (matcher.find()) {
            userFound = matcher.group();
            //       cleanTweet = cleanTweet.replace(" ", "");
            String rawUser = userFound.replace("@", "");
            //       String userHTML="<a href='http://twitter.com/${rawName}'>" + cleanTweet + "</a>"
            tweetText = tweetText.replace(userFound, rawUser);
            listOfUsersMentions.add(userFound);
        }
        // remove punctuations
        tweetText = tweetText.replaceAll("[^a-zA-Z#@ ]", "").toLowerCase();
        // convert text to arraylist
        ArrayList<String> tweetTextToArray = new ArrayList<String>();
        tweetTextToArray.add(tweetText);
        // spit text into tokens
        ArrayList<String> tokens = Tokenizer.tokenizeTweet(tweetText);

        this.result.put("usersMentions", listOfUsersMentions);
        this.result.put("text", tweetTextToArray);
        this.result.put("tokens", tokens);

        return result;
    }
/*
    public void cleanTweet() throws FileNotFoundException {

        String[] topicList = {"tech", "sport", "food", "health", "music", "conspiracy", "cinema", "politics"};

        for (String topic : topicList) {
            File tweetsTopic = new File("tweetsData/" + topic);
            String[] files = tweetsTopic.list();

            for (String filename : files) {
                ArrayList<String> lines = new ArrayList<String>();
                try {
                    Scanner s = new Scanner(new File("tweetsData/" + topic + "/" + filename));
                    while (s.hasNext()) {
                        lines.add(s.next());
                    }
                    s.close();
                } catch (FileNotFoundException e) {
                    throw new FileNotFoundException("Error: " + filename + " not Exist");
                }

                for( String line : lines) {
                    line.replace("{\"text\":", "{\"text\":\t")
                    line.replace( "{")


                }
            }

*/




    public static void main(String[] args) {

        TweetParser tweetParser = new TweetParser();
        Map<String, ArrayList<String>> tweetData = tweetParser.parse("hi guys its      @giudaporcanaehere ! we're helping lots of people like @dioporco! anyway #nottosaybullshit oh #shit here we go # #againg https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html#compile(java.lang.String)");
        System.out.print(tweetData);

  //  for (int i=0; i<topicList.length; i++) {
      //  File[] files = new File(path_to_tweets + '/' + topicList[i]).listFiles();
    /*    try {
            dir = FSDirectory.open(path2index);

            StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
            IndexWriterConfig writer = new IndexWriterConfig(standardAnalyzer);
            IndexWriter iwr = new IndexWriter(dir, writer);

            for (File fi : files) {
                try {
                    File acctweets = new File(path_to_tweets + "/" + fi.getName());

                    BufferedReader br = new BufferedReader(new FileReader(acctweets));

                    String line;

                    while ((line = br.readLine()) != null) {
                        Document index_doc = new Document();
                        //      index_doc = // parseTweet(line);


                        iwr.addDocument(index_doc);
                    }

                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    }
