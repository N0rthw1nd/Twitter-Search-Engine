package com.irproject.restapp;

import com.irproject.CostumStopList;
import com.irproject.CreateIndex;
import com.irproject.crawler.Crawler;
import com.irproject.entities.BagOfWords;
import com.irproject.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.TwitterException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.irproject.crawler.Crawler.crawlTweets;

@SpringBootApplication
public class RestappApplication {

  //  protected static BagOfWords bagOfWords = new BagOfWords();
  //  protected static List<User> userList = bagOfWords.setUsersInterests();;

    public static void main(String[] args) {


        CreateIndex indexCreator = new CreateIndex();

        try {
            CostumStopList costumStopList = new CostumStopList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


//        try {
//            crawlTweets();
//        } catch (TwitterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {0
//            e.printStackTrace();
//        }
//        try {
//           indexCreator.createIndex();
//        } catch (IOException e) {
//           e.printStackTrace();
//        }

        SpringApplication.run(RestappApplication.class, args);




    }

}
