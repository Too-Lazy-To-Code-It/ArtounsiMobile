/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

/**
 *
 * @author aouad
 */
public class Panier {
    private int idpanier;
    private int id_user;
    private int  nbr_produits;
    private double  montant_total;

    public Panier(int id_user, int nbr_produits, double montant_total) {
        this.id_user = id_user;
        this.nbr_produits = nbr_produits;
        this.montant_total = montant_total;
    }

     public Panier() {
     
    }
    
    
    
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

    @Override
    public String toString() {
        return "Panier{" + "nbr_produits=" + nbr_produits + ", montant_total=" + montant_total + '}';
    }

    public void setidpanier(float id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
    
    
    
}
