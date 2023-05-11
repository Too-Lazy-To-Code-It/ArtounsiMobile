/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import models.Category;
import java.sql.Timestamp;
import java.util.Date;


/**
 *
 * @author aouad
 */
public class Produits {
    
    private int idproduit;
    private int id_user;
    private String nom;
    private String description;
    private String image;
    private double prix;
    private Date dateajout;
    private Category categorieProduit;

    public Produits(int idproduit,int id_user, Category categorieProduit,String nom, String description, String image , double prix, Date dateajout) {
        this.idproduit= idproduit;
        this.id_user = id_user;
        this.categorieProduit=categorieProduit;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.dateajout =dateajout ;
       
    }
     public Produits() {
    }





// getters

    public int getIdproduit() {
        return idproduit;
    }

    public int getId_user() {
        return id_user;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public double getPrix() {
        return prix;
    }

    public Date getDateajout() {
        return dateajout;
    }

    public Category getCategorieProduit() {
        return categorieProduit;
    }
//setters

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setDateajout(Date dateajout) {
        this.dateajout = dateajout;
    }

    public void setCategorieProduit(Category categorieProduit) {
        this.categorieProduit = categorieProduit;
    }


//toString


    @Override
    public String toString() {
        return "Produits{" +
                "idproduit=" + idproduit +
                ", id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                ", dateajout=" + dateajout +
                ", categorieProduit=" + categorieProduit +
                '}';
    }
}
