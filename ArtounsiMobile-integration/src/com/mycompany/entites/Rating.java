/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

/**
 *
 * @author achref
 */
public class Rating {
    private int id_rating;
    private int rating;
    private String avg;

    public int getId_rating() {
        return id_rating;
    }

    public void setId_rating(int id_rating) {
        this.id_rating = id_rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }
    
}
