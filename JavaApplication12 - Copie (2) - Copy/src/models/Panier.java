/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author aouad
 */
public class Panier {
     private int idpanier ;
     private int nbr_produits;
     private double montant_total;
    private  int id_user;
    
  
     
    //constructeur 

    public Panier(int idpanier,int id_user ,int nbr_produits, double montant_total) {
   
        this.idpanier=idpanier;
        this.id_user=id_user;
        this.nbr_produits = nbr_produits;
        this.montant_total = montant_total;
     
    }

 

    public Panier() {
    }

     
     //getters


    public int getIdpanier() {
        return idpanier;
    }

    public int getId_user() {
        return id_user;
    }

    public int getNbr_produits() {
        return nbr_produits;
    }
    

    public double getMontant_total() {
        return montant_total;
    }

   

 
 //setters


    public void setIdpanier(int idpanier) {
        this.idpanier = idpanier;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setNbr_produits(int nbr_produits) {
        this.nbr_produits = nbr_produits;
    }

    public void setMontant_total(double montant_total) {
        this.montant_total = montant_total;
    }

 
    //toString


    @Override
    public String toString() {
        return "Panier{" +
                "idpanier=" + idpanier +
                ", nbr_produits=" + nbr_produits +
                ", montant_total=" + montant_total +
                ", id_user=" + id_user +
                '}';
    }
}
