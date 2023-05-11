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
public class FavorisTutorial {
    AllUsers user;
    Tutoriel tutoriel;

    public FavorisTutorial() {
    }

    public FavorisTutorial(AllUsers user, Tutoriel tutoriel) {
        this.user = user;
        this.tutoriel = tutoriel;
    }

    public AllUsers getUser() {
        return user;
    }

    public void setUser(AllUsers user) {
        this.user = user;
    }

    public Tutoriel getTutoriel() {
        return tutoriel;
    }

    public void setTutoriel(Tutoriel tutoriel) {
        this.tutoriel = tutoriel;
    }

    @Override
    public String toString() {
        return "FavorisTutorial{" + "user=" + user + ", tutoriel=" + tutoriel + '}';
    }
    
}
