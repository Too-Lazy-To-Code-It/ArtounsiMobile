/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import models.Category;
import java.util.Date;

/**
 *
 * @author nour2
 */
public class demandeTravail {
    private int idDemande;
     private int idArtiste;
 private  String nomArtiste;
    private String titreDemande;
 private String descriptionDemande;
       private Category categorieDemande;
   private Date dateAjoutDemande;
 private String pdf;

    public demandeTravail() {
    }

    public demandeTravail(int idDemande, int idArtiste, String nomArtiste, String titreDemande, String descriptionDemande, Category categorieDemande, Date dateAjoutDemande) {
        this.idDemande = idDemande;
        this.idArtiste = idArtiste;
        this.nomArtiste = nomArtiste;
        this.titreDemande = titreDemande;
        this.descriptionDemande = descriptionDemande;
        this.categorieDemande = categorieDemande;
        this.dateAjoutDemande = dateAjoutDemande;
    }

    public demandeTravail(int idArtiste, String nomArtiste, String titreDemande, String descriptionDemande, Category categorieDemande, Date dateAjoutDemande) {
        this.idArtiste = idArtiste;
        this.nomArtiste = nomArtiste;
        this.titreDemande = titreDemande;
        this.descriptionDemande = descriptionDemande;
        this.categorieDemande = categorieDemande;
        this.dateAjoutDemande = dateAjoutDemande;
    }


  
//getters et setters
    public int getIdDemande() {
        return idDemande;
    }

    public String getTitreDemande() {
        return titreDemande;
    }

    public String getDescriptionDemande() {
        return descriptionDemande;
    }

   

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public void setTitreDemande(String titreDemande) {
        this.titreDemande = titreDemande;
    }

    public void setDescriptionDemande(String descriptionDemande) {
        this.descriptionDemande = descriptionDemande;
    }

    

    public Date getDateAjoutDemande() {
        return dateAjoutDemande;
    }

    public void setDateAjoutDemande(Date dateAjoutDemande) {
        this.dateAjoutDemande = dateAjoutDemande;
    }

    public void setIdArtiste(int idArtiste) {
        this.idArtiste = idArtiste;
    }

    public void setNomArtiste(String nomArtiste) {
        this.nomArtiste = nomArtiste;
    }

    public int getIdArtiste() {
        return idArtiste;
    }

    public String getNomArtiste() {
        return nomArtiste;
    }

    public void setCategorieDemande(Category categorieDemande) {
        this.categorieDemande = categorieDemande;
    }
    public Category getCategorieDemande() {
        return categorieDemande;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
    
//display

    @Override
    public String toString() {
        return "demandeTravail{" + "idDemande=" + idDemande + ", idArtiste=" + idArtiste + ", nomArtiste=" + nomArtiste + ", titreDemande=" + titreDemande + ", descriptionDemande=" + descriptionDemande + ", categorieDemande=" + categorieDemande + ", dateAjoutDemande=" + dateAjoutDemande + ", pdf=" + pdf + '}';
    }

    
    

       
    
}
