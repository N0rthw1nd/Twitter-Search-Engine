package com.irproject.restapp;
//package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.io.IOException;

//import javax.validation.Valid;

import com.irproject.Searcher;
import net.minidev.json.JSONArray;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.web.bind.annotation.*;

//import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
//import net.guides.springboot2.springboot2jpacrudexample.model.Employee;
//import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class SearchController {

 //   @Autowired
 //   private  employeeRepository;

    private Searcher searcher = new Searcher();
//    private BagOfWords bagOfWords = new BagOfWords();
//

//    params = {'user': "", 'account_search': False, 'hashtag_search': False, 'personalized': False }


    @GetMapping("/search/{queryString}")
    public JSONArray getSearchResults(@PathVariable(value = "queryString") String queryString, @RequestParam(value = "user") String username, @RequestParam(value = "personalized") String personalizedSearch, @RequestParam(value = "account_search") String searchByUser, @RequestParam(value = "hashtag_search") String searchByHashtag) throws IOException, ParseException {

        System.out.println("byHshtag " +  searchByHashtag);
        System.out.println("byUser " +  searchByUser);
        System.out.println("Personal " +  personalizedSearch);

        boolean personal = false;
        boolean byHashtag = false;
        boolean byUser = false;

        if(personalizedSearch.equals("True")){
            personal = true;
            }

        if(searchByHashtag.equals("True")){
            byHashtag = true;
        }
        if(searchByUser.equals("True")){
            byUser = true;
        }
        System.out.println("seaorch by hashtag : " + byUser );
        System.out.println("seaorch by user : " +  byUser );
        System.out.println("seaorch personalized : " + personal );
        System.out.println("USERNAME : " +  username );

//        ArrayList<UserInterest> availableTopics = this.bagOfWords.getUsersInterests();
//        UserInterest topicToSearch = new UserInterest();
//        for (int i=0; i<availableTopics.size(); i++) {
//            if(availableTopics.get(i).getTopicName() == topic){
//                topicToSearch = availableTopics.get(i);
//            }
//        }
        JSONArray results = searcher.searchTweets(username, queryString, personal, byUser, byHashtag);

        return results;
    }


//// UserInterest topicalInterest, String queryString, boolean personalizedSearch, boolean searchByUser, boolean searchByHashtag
//    @GetMapping("/search/user/{queryString}")
//    public JSONArray getPersonalizedResults(@PathVariable(value = "queryString") String queryString, @PathVariable(value = "topic") String topic, boolean personalizedSearch, boolean searchByUser, boolean searchByHashtag) throws IOException, ParseException {
//
//        personalizedSearch = true;
//        searchByUser = false;
//        searchByHashtag = false;
//        ArrayList<UserInterest> availableTopics = this.bagOfWords.getUsersInterests();
//        UserInterest topicToSearch = new UserInterest();
//        for (int i=0; i<availableTopics.size(); i++) {
//            if(availableTopics.get(i).getTopicName() == topic){
//                topicToSearch = availableTopics.get(i);
//            }
//        }
//        JSONArray results = searcher.searchTweets(topicToSearch, queryString, personalizedSearch, searchByUser,searchByHashtag);
//
//        return results;
//    }


   /* @GetMapping("/search/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

   */


}
