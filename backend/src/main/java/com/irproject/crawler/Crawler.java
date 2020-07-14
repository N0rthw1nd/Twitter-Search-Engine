package com.irproject.crawler;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Crawler{

   public static void crawlTweets() throws TwitterException, IOException,FileNotFoundException {
       ConfigurationBuilder cb = new ConfigurationBuilder();
       cb.setDebugEnabled(true)
               .setOAuthConsumerKey("RqItSPCQVhduU2WrrrKZmK0CH")
               .setOAuthConsumerSecret("FXT4fl74FSg7jLRqBgJqeFO3bFqno7KYpxxnTcRLdtAz1lNKXQ")
               .setOAuthAccessToken("1010918385113272321-Q8bYVaglaLzAxEOpXjMzi5p6YXFlGF")
               .setOAuthAccessTokenSecret("lBYoJpFtxTiiZTqnk2DkLP8usnyaJ7iNse0NOLjj22g1a");
       TwitterFactory tf = new TwitterFactory(cb.build());
       Twitter twitter = tf.getInstance();
      // String [] categoryList = {"tech", "cinema", "sport","food", "news", "music",
       String [] categoryList = {  "news"};
       for (int i=0; i<categoryList.length; i++) {
           ArrayList<String> users = new ArrayList<String>();
           try {
               Scanner s = new Scanner(new File("./userLists/usersList_" + categoryList[i] + ".txt"));
               while (s.hasNext()){
                   users.add(s.next());
               }
               s.close();
           }
           catch(FileNotFoundException e) {
              throw new FileNotFoundException("Error: usersList_" + categoryList[i] + ".txt not Exist");
           }


           // iterator on the users per category
           Iterator<String> usersIterator = users.iterator();

           while (usersIterator.hasNext()) {
               try {
                   sleep(30000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               String user = usersIterator.next();

               System.out.println("Extracting tweet of user:" + user);
               List<Status> statuses = new ArrayList<Status>();

               int pageno = 1;
               // extract tweets from user timeline
               while (true) {

                   try {

                       int size = statuses.size();
                       Paging page = new Paging(pageno++, 200);
                       statuses.addAll(twitter.getUserTimeline(user, page));
                       if (statuses.size() == size)
                           break;
                   } catch (TwitterException e) {

                       e.printStackTrace();
                       System.out.println("The crawl will restart after 15 minutes");
                       try {
                           sleep(1500000);
                       } catch (InterruptedException e1) {
                           e1.printStackTrace();
                       }
                   }

               }


               File tweetDirUsers = new File("tweetsData");
               if (!tweetDirUsers.exists()) {
                   tweetDirUsers.mkdir();
               }
               File tweetsCategory = new File("tweetsData/" + categoryList[i]);
               if(!tweetsCategory.exists()) {
                   tweetsCategory.mkdir();
               }
               System.out.println(tweetsCategory.getAbsolutePath());
               File file = new File(tweetsCategory.getAbsolutePath() + "/" + user + "_tweets.txt");
               // if not exists create category directory for storing tweets
               FileWriter fw = new FileWriter(file, true);
               BufferedWriter bw = new BufferedWriter(fw);
               for (Status tweet : statuses) {
                   JSONObject obj = new JSONObject();
                   obj.put("text", tweet.getText());
                   obj.put("accountName", tweet.getUser().getScreenName());
                   obj.put("date", tweet.getCreatedAt());
                   obj.put("geoLocation", tweet.getGeoLocation());
                   obj.put("retweetCount", tweet.getRetweetCount());
                   obj.put("favoritesCount", tweet.getFavoriteCount());

                   bw.write(obj.toString() + "\n");


                   //  bw.write("@" + tweet.getUser().getScreenName() + ":" + tweet.getCreatedAt() + " - " + tweet.getText() + "\n");
               }
               bw.close();

           }
        }
    }
}
