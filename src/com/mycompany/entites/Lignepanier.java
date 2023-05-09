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
public class Lignepanier {
    private int idlignepanier;
    private Panier panier ; 
    private Produits produit ; 

    public Lignepanier(int idlignepanier, Panier panier, Produits produit) {
        this.idlignepanier = idlignepanier;
        this.panier = panier;
        this.produit = produit;
    }

      public Lignepanier( Panier panier, Produits produit) {
        
        this.panier = panier;
        this.produit = produit;
    }
    
     
public Lignepanier() {
        
    }

    public int getIdlignepanier() {
        return idlignepanier;
    }

    public Panier getPanier() {
        return panier;
    }

    public Produits getProduit() {
        return produit;
    }

    public void setIdlignepanier(int idlignepanier) {
        this.idlignepanier = idlignepanier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public void setProduit(Produits produit) {
        this.produit = produit;
    }
public void setProduit(String nom, double prix) {
    this.produit = new Produits(nom, prix);
}
    @Override
    public String toString() {
        return "Lignepanier{" + "idlignepanier=" + idlignepanier + ", panier=" + panier + ", produit=" + produit + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
}
