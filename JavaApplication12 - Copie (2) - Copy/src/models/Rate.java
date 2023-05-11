/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author achref
 */
public class Rate {
    private AllUsers rater,participator;
    private Challenge challenge;
    private Double rating;

    public Rate() {
    }

    public Rate(AllUsers rater, AllUsers participator, Challenge challenge, Double rating) {
        this.rater = rater;
        this.participator = participator;
        this.challenge = challenge;
        this.rating = rating;
    }

    public AllUsers getRater() {
        return rater;
    }

    public void setRater(AllUsers rater) {
        this.rater = rater;
    }

    public AllUsers getParticipator() {
        return participator;
    }

    public void setParticipator(AllUsers participator) {
        this.participator = participator;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rate{" + "rater=" + rater + ", participator=" + participator + ", challenge=" + challenge + ", rating=" + rating + '}';
    }


    
}

