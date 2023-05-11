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
public class Tutoriel {
    
    private int id_tutoriel;
    private String pathimg;
    private String title;
    private String description;
    private int niveau;
    private int id_artist;
    private String avg_rating;
    private String nbr_favoris;
    private String id_categorie;

    public Tutoriel() {
    }

    public Tutoriel(String title, String description,String pathimg, int niveau, int id_artist, String id_categorie) {
        this.pathimg = pathimg;
        this.title = title;
        this.description = description;
        this.niveau = niveau;
        this.id_artist = id_artist;
        this.id_categorie = id_categorie;
    }
    
    

    public Tutoriel(int id_tutoriel, String title, String description, String pathimg, int niveau, int id_artist, String id_categorie) {
        this.id_tutoriel = id_tutoriel;
        this.pathimg = pathimg;
        this.title = title;
        this.description = description;
        this.niveau = niveau;
        this.id_artist = id_artist;
        this.id_categorie = id_categorie;
    }
    
    

    public int getId_tutoriel() {
        return id_tutoriel;
    }

    public void setId_tutoriel(int id_tutoriel) {
        this.id_tutoriel = id_tutoriel;
    }

    public String getPathimg() {
        return pathimg;
    }

    public void setPathimg(String pathimg) {
        this.pathimg = pathimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }

    public String getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(String id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getNbr_favoris() {
        return nbr_favoris;
    }

    public void setNbr_favoris(String nbr_favoris) {
        this.nbr_favoris = nbr_favoris;
    }

    
}
