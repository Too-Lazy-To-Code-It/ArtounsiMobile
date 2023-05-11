/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entites;
import java.util.Date;
public class Offre {
    
    //var
    private int idoffre;
    private int id_user;
    private String titreoffre;
    private String descriptionoffre;
    private String categorieoffre;
    private String nickname ;
    private  Date dateajoutoofre ;
    private String typeoffre ;
    private String localisationoffre;
    private int idcategorie ;

    public Offre(int idoffre, int id_user, String titreoffre, String descriptionoffre, String categorieoffre, String nickname, Date dateajoutoofre, String typeoffre, String localisationoffre, int idcategorie) {
        this.idoffre = idoffre;
        this.id_user = id_user;
        this.titreoffre = titreoffre;
        this.descriptionoffre = descriptionoffre;
        this.categorieoffre = categorieoffre;
        this.nickname = nickname;
        this.dateajoutoofre = dateajoutoofre;
        this.typeoffre = typeoffre;
        this.localisationoffre = localisationoffre;
        this.idcategorie = idcategorie;
    }
    
    
  
 
    public Offre() {
    }

    public Offre(String titreoffre, String descriptionoffre, String typeoffre, String localisationoffre, int idcategorie) {
        this.titreoffre = titreoffre;
        this.descriptionoffre = descriptionoffre;
        this.typeoffre = typeoffre;
        this.localisationoffre = localisationoffre;
        this.idcategorie = idcategorie;
    }

    public Offre(int idoffre, String text, String text0, String text1, String text2, int parseInt) {
        this.idoffre=idoffre;
       this.titreoffre = text;
        this.descriptionoffre = text0;
        this.typeoffre = text1;
        this.localisationoffre = text2;
        this.idcategorie = parseInt;
    }

   

    
    public int getIdoffre() {
        return idoffre;
    }

    public int getId_user() {
        return id_user;
    }

    public String getTitreoffre() {
        return titreoffre;
    }

    public String getDescriptionoffre() {
        return descriptionoffre;
    }

    public String getCategorieoffre() {
        return categorieoffre;
    }

    public String getNickname() {
        return nickname;
    }

    public Date getDateajoutoofre() {
        return dateajoutoofre;
    }

    public String getTypeoffre() {
        return typeoffre;
    }

    public String getLocalisationoffre() {
        return localisationoffre;
    }

    public int getIdcategorie() {
        return idcategorie;
    }

    public void setIdoffre(int idoffre) {
        this.idoffre = idoffre;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setTitreoffre(String titreoffre) {
        this.titreoffre = titreoffre;
    }

    public void setDescriptionoffre(String descriptionoffre) {
        this.descriptionoffre = descriptionoffre;
    }

    public void setCategorieoffre(String categorieoffre) {
        this.categorieoffre = categorieoffre;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDateajoutoofre(Date dateajoutoofre) {
        this.dateajoutoofre = dateajoutoofre;
    }

    public void setTypeoffre(String typeoffre) {
        this.typeoffre = typeoffre;
    }

    public void setLocalisationoffre(String localisationoffre) {
        this.localisationoffre = localisationoffre;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    @Override
    public String toString() {
        return "Offre{" + "idoffre=" + idoffre + ", id_user=" + id_user + ", titreoffre=" + titreoffre + ", descriptionoffre=" + descriptionoffre + ", categorieoffre=" + categorieoffre + ", nickname=" + nickname + ", dateajoutoofre=" + dateajoutoofre + ", typeoffre=" + typeoffre + ", localisationoffre=" + localisationoffre + ", idcategorie=" + idcategorie + '}';
    }

  
    
   
    
   
    
    
}
