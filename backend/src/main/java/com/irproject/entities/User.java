package com.irproject.entities;

import java.util.ArrayList;

public class User {

   // 10 utenti, ognuno con o 5 o meno (3) categorie specializzate con almeno un topical interest

    private String name;
    private ArrayList<UserInterest> userInterests;
    private int id;
    private String imagePath;
    /**
     * @return
     */
    public ArrayList<UserInterest> getUserInterests() {
        return userInterests;
    }

    /**
     * @param name
     */
    public User (String name, int id, String imagePath){
        this.name = name;
        this.id = id;
        this.imagePath = imagePath;
    }

    /**
     * @param name
     * @param userInterests
     */
    public User(String name, ArrayList<UserInterest> userInterests, int id ){
        this.name = name;
        this.userInterests = userInterests;
        this.id = id;
    }

    /**
     * @param userInterests
     */
    public void setUserInterests(ArrayList<UserInterest> userInterests) {
        this.userInterests = userInterests;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }
}
