/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import models.Panier;
/**
 *
 * @author aouad
 */
public class LignePanier {


    private int idlignepanier;
    private Panier panier;
    private Produits produit;
    private Date dateajout;

    public LignePanier(int idlignepanier, Panier panier, Produits produit, Date dateajout) {
        this.idlignepanier= idlignepanier;
        this.panier = panier;
        this.produit = produit;
        this.dateajout = dateajout;
    }

    public LignePanier() {
    }

    public int getIdlignepanier() {
        return idlignepanier;
    }

    public void setIdlignepanier(int idlignepanier) {
        this.idlignepanier = idlignepanier;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Produits getProduit() {
        return produit;
    }

    public void setProduit(Produits produit) {
        this.produit = produit;
    }

    public Date getDateajout() {
        return dateajout;
    }

    public void setDateajout(Date dateajout) {
        this.dateajout = dateajout;
    }

    @Override
    public String toString() {
        return "LignePanier{" +
                "idLignePanier=" + idlignepanier +
                ", panier=" + panier +
                ", produit=" + produit +
                ", dateAjout=" + dateajout +
                '}';
    }
}