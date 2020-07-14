package com.irproject.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BagOfWords {

    public User Galadriel = new User ("Galadriel", 0, "/static/img/galadriel.jpg");
    public User Arwen = new User ("Arwen", 1, "/static/img/arwen.jpg");
    public User Leia = new User ("Leia", 2, "/static/img/leia.jpg");
    public User Eowyn = new User ("Eowyn", 3, "/static/img/eowyn.jpg");
    public User Hermione = new User ("Hermione", 4,"/static/img/hermione.jpg");
    public User Gandalf = new User ("Gandalf", 5, "/static/img/gandalf.jpg");
    public User Luke = new User ("Luke", 6, "/static/img/luke.jpg");
    public User Aragorn = new User ("Aragorn", 7, "/static/img/aragorn.jpg");
    public User Legolas = new User ("Legolas", 8, "/static/img/legolas.jpg");
    public User Satoshi = new User ("Satoshi Nakamoto", 9, "/static/img/satoshi.jpg");

    private String pathToTopicalInterests = "./user_interests/";
    private ArrayList<UserInterest> usersInterests = new ArrayList<UserInterest>();

    public BagOfWords (){
        usersInterests = this.createUsersBagOfWords();
        this.setUsersInterests();
    }

    public ArrayList<UserInterest> getUsersInterests(){
            return this.usersInterests;
    }
    /**
     * Give the topical interest name and category returns the topical interest with the associated bag of words
     * @param topicName Name of the topic to be related to the file's bag of word
     * @param category Category for the given topical interest
     * @return The topical interest object with the related bag of words describing it
     */
    public UserInterest createTopicalInterestBOW (String topicName, String category) throws FileNotFoundException {

        UserInterest userInterest = new UserInterest(topicName, category);

        File topicFile = new File(pathToTopicalInterests + "/" + category + "/" + topicName + ".txt");
        userInterest.setBagOfWords(userInterest.bagOfWordsFromFile(topicFile));

        return userInterest;
    }

    /**
     * Fill each user's bag of words with its topical interests
     * @return list of users with given bag of words
     */
    public ArrayList<UserInterest> createUsersBagOfWords() {

        try {
            // defines the list of available topical interests
            usersInterests.add(createTopicalInterestBOW("tvseries", "cinema")); // 0
            usersInterests.add(createTopicalInterestBOW("superheroes", "cinema")); // 1
            usersInterests.add(createTopicalInterestBOW("cartoons", "cinema")); // 2
            usersInterests.add(createTopicalInterestBOW("fantasy", "cinema")); // 3
            usersInterests.add(createTopicalInterestBOW("Juventus", "sport")); // 4
            usersInterests.add(createTopicalInterestBOW("Milan", "sport")); // 5
            usersInterests.add(createTopicalInterestBOW("FIFA", "sport")); // 6
            usersInterests.add(createTopicalInterestBOW("Coronavirus", "news")); // 7
            usersInterests.add(createTopicalInterestBOW("BlackLivesMatter", "news")); // 8
            usersInterests.add(createTopicalInterestBOW("Pride", "news")); // 9
            usersInterests.add(createTopicalInterestBOW("vegan", "food")); // 10
            usersInterests.add(createTopicalInterestBOW("italianfood", "food")); // 11
            usersInterests.add(createTopicalInterestBOW("meat", "food")); // 12
            usersInterests.add(createTopicalInterestBOW("sweets", "food")); // 13
            usersInterests.add(createTopicalInterestBOW("Linux", "tech")); // 14
            usersInterests.add(createTopicalInterestBOW("Apple", "tech")); // 15
            usersInterests.add(createTopicalInterestBOW("Microsoft", "tech")); // 16
            usersInterests.add(createTopicalInterestBOW("Google", "tech")); // 17
            usersInterests.add(createTopicalInterestBOW("Pop", "music")); // 18
            usersInterests.add(createTopicalInterestBOW("Rock", "music")); // 19
            usersInterests.add(createTopicalInterestBOW("Metal", "music")); // 20
            usersInterests.add(createTopicalInterestBOW("Jazz", "music")); // 21

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return usersInterests;

    }


    public List<User> setUsersInterests(){
        // assign the Bag Of Words to each user
        Galadriel.setUserInterests(new ArrayList<UserInterest>(){{
                                        add(usersInterests.get(0));
                                        add(usersInterests.get(21));
                                        add(usersInterests.get(6));
                                    }});
        Arwen.setUserInterests(new ArrayList<UserInterest>(){{
                                        add(usersInterests.get(1));
                                        add(usersInterests.get(4));
                                        add(usersInterests.get(17));
        }});
        Leia.setUserInterests(new ArrayList<UserInterest>(){{
                                        add(usersInterests.get(2));
                                        add(usersInterests.get(5));
                                        add(usersInterests.get(20));
        }});
        Eowyn.setUserInterests(new ArrayList<UserInterest>(){{
                                        add(usersInterests.get(8));
                                        add(usersInterests.get(11));
                                        add(usersInterests.get(15));
        }});
        Hermione.setUserInterests(new ArrayList<UserInterest>(){{
                                        add(usersInterests.get(3));
                                        add(usersInterests.get(12));
                                        add(usersInterests.get(16));
        }});
        Gandalf.setUserInterests(new ArrayList<UserInterest>(){{
                                        add(usersInterests.get(9));
                                        add(usersInterests.get(13));
                                        add(usersInterests.get(17));
        }});
        Luke.setUserInterests(new ArrayList<UserInterest>(){{
                                        add(usersInterests.get(7));
                                        add(usersInterests.get(14));
                                        add(usersInterests.get(18));
        }});
        Aragorn.setUserInterests(new ArrayList<UserInterest>(){{
            add(usersInterests.get(3));
            add(usersInterests.get(10));
            add(usersInterests.get(17));
        }});
        Legolas.setUserInterests(new ArrayList<UserInterest>(){{
            add(usersInterests.get(8));
            add(usersInterests.get(11));
            add(usersInterests.get(19));
        }});
        Satoshi.setUserInterests(new ArrayList<UserInterest>(){{
            add(usersInterests.get(14));
            add(usersInterests.get(13));
            add(usersInterests.get(20));
        }});

        List<User> listOfUsers = Arrays.asList( Galadriel, Arwen, Leia, Eowyn, Hermione, Gandalf, Luke, Aragorn, Legolas, Satoshi) ;
        return listOfUsers;


     }
    }

