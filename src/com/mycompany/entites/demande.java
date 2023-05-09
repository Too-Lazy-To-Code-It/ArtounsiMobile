/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entites;
import java.util.Date;
public class demande {
    
    //var
    private int idDemande;
    private int id_user;
    private String titreDemande;
    private String descriptionDemande;
    private String categoriedemande;
    private String nickname ;
    private  Date dateajoutdemande ;
  private String pdf;
    private int idcategorie ;

    public demande(int idDemande, int id_user, String titreDemande, String descriptionDemande, String categoriedemande, String nickname, Date dateajoutdemande, String pdf, int idcategorie) {
        this.idDemande = idDemande;
        this.id_user = id_user;
        this.titreDemande = titreDemande;
        this.descriptionDemande = descriptionDemande;
        this.categoriedemande = categoriedemande;
        this.nickname = nickname;
        this.dateajoutdemande = dateajoutdemande;
        this.pdf = pdf;
        this.idcategorie = idcategorie;
    }

    public demande(int id_user, String titreDemande, String descriptionDemande, String categoriedemande, String nickname, Date dateajoutdemande, String pdf, int idcategorie) {
        this.id_user = id_user;
        this.titreDemande = titreDemande;
        this.descriptionDemande = descriptionDemande;
        this.categoriedemande = categoriedemande;
        this.nickname = nickname;
        this.dateajoutdemande = dateajoutdemande;
        this.pdf = pdf;
        this.idcategorie = idcategorie;
    }

    public demande() {
       
    }

    public demande(int idDemande, String text, String text0, String text1, int parseInt) {
      this.idDemande = idDemande;
        this.titreDemande = text;
        this.descriptionDemande = text0;
      
        this.pdf = text1;
        this.idcategorie = parseInt;
    }

    public demande(String text, String text0, String text1, int parseInt) {
      this.titreDemande = text;
        this.descriptionDemande = text0;
      
        this.pdf = text1;
        this.idcategorie = parseInt;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public int getId_user() {
        return id_user;
    }

    public String getTitreDemande() {
        return titreDemande;
    }

    public String getDescriptionDemande() {
        return descriptionDemande;
    }

    public String getCategoriedemande() {
        return categoriedemande;
    }

    public String getNickname() {
        return nickname;
    }

    public Date getDateajoutdemande() {
        return dateajoutdemande;
    }

    public String getPdf() {
        return pdf;
    }

    public int getIdcategorie() {
        return idcategorie;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setTitreDemande(String titreDemande) {
        this.titreDemande = titreDemande;
    }

    public void setDescriptionDemande(String descriptionDemande) {
        this.descriptionDemande = descriptionDemande;
    }

    public void setCategoriedemande(String categoriedemande) {
        this.categoriedemande = categoriedemande;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDateajoutdemande(Date dateajoutdemande) {
        this.dateajoutdemande = dateajoutdemande;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    @Override
    public String toString() {
        return "demande{" + "idDemande=" + idDemande + ", id_user=" + id_user + ", titreDemande=" + titreDemande + ", descriptionDemande=" + descriptionDemande + ", categoriedemande=" + categoriedemande + ", nickname=" + nickname + ", dateajoutdemande=" + dateajoutdemande + ", pdf=" + pdf + ", idcategorie=" + idcategorie + '}';
    }

  
}
