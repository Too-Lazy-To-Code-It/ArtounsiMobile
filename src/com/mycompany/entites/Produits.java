/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;


import java.util.Date;

/**
 *
 * @author aouad
 */
public class Produits {
    private int idproduit;
    private String nom;
    private String description;
    private String image;
    private Category category;
    
    private double prix;
    private Date dateajout;

    public Produits(int idproduit, String nom, String description, String image, Category category,double prix, Date dateajout) {
        this.idproduit = idproduit;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.category = category;
        
        this.prix = prix;
        this.dateajout = dateajout;
    }
    public Produits( String nom, String description, String image, Category category,double prix, Date dateajout) {
     
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.category = category;
       
        this.prix = prix;
        this.dateajout = dateajout;
    }
     public Produits( String nom,double prix) {
     
        this.nom = nom;
    
        this.prix = prix;
    
    }
     
   
public Produits() {
        
    }

 
    public int getIdproduit() {
        return idproduit;
     }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }
 
   
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

   

   

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDateajout() {
        return dateajout;
    }

    public void setDateajout(Date dateajout) {
        this.dateajout = dateajout;
    }

    @Override
    public String toString() {
        return "Produits{" + "idproduit=" + idproduit + ", nom=" + nom + ", description=" + description + ", image=" + image + ", category=" + category + ", prix=" + prix + ", dateajout=" + dateajout + '}';
    }



   

   
    
    
    
    
    
    
    
}
