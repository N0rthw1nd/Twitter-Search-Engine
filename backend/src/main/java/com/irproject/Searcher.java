package com.irproject;

import com.irproject.entities.BagOfWords;
import com.irproject.entities.UserInterest;
import com.irproject.entities.User;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queries.function.FunctionScoreQuery;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;



public class Searcher {

    private ArrayList<String> searchResults;
    private Path pathToIndex = Paths.get("./indexes/");
    private float topicRelevance = (float) 1.3;
    private static BagOfWords bagOfWords = new BagOfWords();
    private static List<User> userList = bagOfWords.setUsersInterests();;



    public Searcher(){
        this.searchResults = new ArrayList<String>();
    }

    /**
     * @param userName Name of the user that will perform the search
     * @param queryString Value of the query
     * @param scoreretweet option for costum search with ordering by number of retweets
     * @param searchByHashtag option to search and index by hashtags
     * @return
     */
    public JSONArray searchTweets(String userName, String queryString, boolean personalizedSearch, boolean scoreretweet, boolean searchByHashtag) throws IOException, ParseException {

        Directory indexDir = FSDirectory.open(pathToIndex);
        DirectoryReader indexReader = DirectoryReader.open(indexDir);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        QueryParser queryParser;
        User usersearching = userList.get(0);
        for (User user : userList ) {
            if (user.getName().equals(userName)) {
                usersearching = user;
                break;
            }
        }

       // QueryAutoStopWordAnalyzer(Analyzer delegate, IndexReader indexReader);

        queryParser = new QueryParser( "text", new StandardAnalyzer(CostumStopList.getCostumStopList()));

       if (searchByHashtag){
            queryParser = new QueryParser("hashtag", new StandardAnalyzer(CostumStopList.getCostumStopList()));
            queryString = queryString.replaceAll("#", "");
            System.out.println("Sto effettuando la ricerca dei tweet con l'hashtag " + queryString);

        }


        // search the term inside the selected field

        Query query = queryParser.parse(queryString);
        System.out.println("query: " + query.toString());
        TopDocs topDocs = indexSearcher.search(query, 100);
        System.out.println("Documents found " + topDocs.totalHits);
        // sorting by retweet if asked
        Sort sort = new Sort();
        sort.setSort(new SortField("retweet", SortField.Type.INT, true));

        if(scoreretweet && searchByHashtag){

            topDocs = indexSearcher.search(query, 100, sort);
        }


        if(scoreretweet && !searchByHashtag) {
            FunctionScoreQuery functionScoreQuery = FunctionScoreQuery.boostByValue(query, (DoubleValuesSource.fromIntField("retweet")));
            topDocs = indexSearcher.search(functionScoreQuery, 100, sort);

        }

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        indexDir.close();

            // personalize results based the user Topical interest if required
        if(personalizedSearch){

            // the document score is increased by 1.15
            for (UserInterest topicInterest : usersearching.getUserInterests()){
                for (String token : topicInterest.getBagOfWords()){

                    for (int j = 0; j<scoreDocs.length; j++){

                        String scoretext = indexSearcher.doc(scoreDocs[j].doc)
                                                        .getField("text")
                                                        .stringValue();
                        int fromIndex = 0;
                        int count = 0;
                        while ((fromIndex = scoretext.indexOf(token, fromIndex)) != -1 ){

                            count++;
                            fromIndex++;

                        }



                        // if token is fould increased the score of the the given document
                        if( count > 0){
                            System.out.println(count);
                            System.out.println(token);
                            scoreDocs[j].score = scoreDocs[j].score * this.topicRelevance * count * count;
                            System.out.println(scoreDocs[j].score);
                            System.out.println(scoretext);

                        }

                    }
                }
            }

        }
        else{
            System.out.println("General search without preferences");
        }


        Comparator<ScoreDoc> comparator = new Comparator<ScoreDoc>(){
            public int compare(ScoreDoc doc1, ScoreDoc doc2){
                return (int) ((doc2.score - doc1.score)*1000);
            }
        };
        if(personalizedSearch) {
            Arrays.sort(scoreDocs, comparator);
        }
       // ArrayList<Document> searchResults = new ArrayList<Document>();

        JSONArray searchResults = new JSONArray();
        for(int i = 0; i < scoreDocs.length; i++) {
            JSONObject documentjson = new JSONObject();
            JSONArray hashtags = new JSONArray();
            Document foundDocument = indexSearcher.doc(scoreDocs[i].doc);
            String text = foundDocument.getField("text").stringValue();
            String account = foundDocument.getField("account").stringValue();
            IndexableField[] hashtagsFields = foundDocument.getFields("hashtag");
            String date = foundDocument.getField("date").stringValue();
            Number numberOfWords = foundDocument.getField("numberOfWords").numericValue();
            Number numberOfRetweet = foundDocument.getField("retweetCount").numericValue();
            Number numberOfFavourite = foundDocument.getField("favouriteCount").numericValue();
//            ArrayList<String> hashtags = new ArrayList<String>();
            for (IndexableField obj : hashtagsFields  ) {
                hashtags.add(obj.stringValue());
            }
            documentjson.put("text", text);
            documentjson.put("account", account);
            documentjson.put("date", date);
            documentjson.put("numberOfWords", numberOfWords);
            documentjson.put("numberOfRetweet", numberOfRetweet);
            documentjson.put("numberOfFavourite", numberOfFavourite);
            documentjson.put("hashtags", hashtags);

            searchResults.add(documentjson);
        }
        System.out.println(usersearching.getName());
        return searchResults;
    }
    public float getTopicRelevance() {
        return topicRelevance;
    }
    public ArrayList<String> getSearchResults() {
        return searchResults;
    }

    public void setTopicRelevance(float topicRelevance) {
        this.topicRelevance = topicRelevance;
    }
}
