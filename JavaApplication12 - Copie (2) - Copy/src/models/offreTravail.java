/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author nour2
 */
public class offreTravail {
 private int idOffre;
 private String titreOffre;
 private String descriptionOffre;
 private Category categorieOffre;
 private int idStudio;
 private  String nomStudio;
 private Date dateAjoutOffre;
 private String typeOffre ;
 private String localisationOffre;
 
   //cons
    
    public offreTravail() {
    }

    public offreTravail(int idOffre, String titreOffre, String descriptionOffre, Category categorieOffre, int idStudio, String nomStudio, Date dateAjoutOffre, String typeOffre, String localisationOffre) {
        this.idOffre = idOffre;
        this.titreOffre = titreOffre;
        this.descriptionOffre = descriptionOffre;
        this.categorieOffre = categorieOffre;
        this.idStudio = idStudio;
        this.nomStudio = nomStudio;
        this.dateAjoutOffre = dateAjoutOffre;
        this.typeOffre = typeOffre;
        this.localisationOffre = localisationOffre;
    }

    public offreTravail(String titreOffre, String descriptionOffre, Category categorieOffre, int idStudio, String nomStudio, Date dateAjoutOffre, String typeOffre, String localisationOffre) {
        this.titreOffre = titreOffre;
        this.descriptionOffre = descriptionOffre;
        this.categorieOffre = categorieOffre;
        this.idStudio = idStudio;
        this.nomStudio = nomStudio;
        this.dateAjoutOffre = dateAjoutOffre;
        this.typeOffre = typeOffre;
        this.localisationOffre = localisationOffre;
    }

    
    
    

    public String getTypeOffre() {
        return typeOffre;
    }

    public void setTypeOffre(String typeOffre) {
        this.typeOffre = typeOffre;
    }

    public void setLocalisationOffre(String localisationOffre) {
        this.localisationOffre = localisationOffre;
    }

    public String getLocalisationOffre() {
        return localisationOffre;
    }

    

    

    public int getIdStudio() {
        return idStudio;
    }
    
    public void setIdStudio(int idStudio) {    
        this.idStudio = idStudio;
    }

    //getters and setters
    public void setNomStudio(String nomStudio) {
        this.nomStudio = nomStudio;    
    }

    public String getNomStudio() {
        return nomStudio;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public void setCategorieOffre(Category categorieOffre) {
        this.categorieOffre = categorieOffre;
    }

    public void setTitreOffre(String titreOffre) {
        this.titreOffre = titreOffre;
    }

    public void setDescriptionOffre(String descriptionOffre) {
        this.descriptionOffre = descriptionOffre;
    }

   
    public int getIdOffre() {
        return idOffre;
    }

    public String getTitreOffre() {
        return titreOffre;
    }

    public String getDescriptionOffre() {
        return descriptionOffre;
    }

    public Category getCategorieOffre() {
        return categorieOffre;
    }

    

    public void setDateAjoutOffre(Date dateAjoutOffre) {
        this.dateAjoutOffre = dateAjoutOffre;
    }

    public Date getDateAjoutOffre() {
        return dateAjoutOffre;
        }

    //display 

    @Override
    public String toString() {
        return  "idOffre=" + idOffre + ", titreOffre=" + titreOffre + ", descriptionOffre=" + descriptionOffre + ", categorieOffre=" + categorieOffre + ", idStudio=" + idStudio + ", nomStudio=" + nomStudio + ", dateAjoutOffre=" + dateAjoutOffre + ", typeOffre=" + typeOffre + ", localisationOffre=" + localisationOffre + '}';
    }

   

   
    
    
    
}
