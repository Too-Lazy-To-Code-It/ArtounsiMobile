/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
/**
 *
 * @author achref
 */
public class Tutoriel {

    private int ID_Tutoriel, Niveau;
    private String Title, Description, PathImg;
    private Category categorie;
    private AllUsers Creator;

    public Tutoriel() {
    }

    public Tutoriel(int ID_Tutoriel, int Niveau, String Title, String Description, String PathImg, Category categorie, AllUsers Creator) {
        this.ID_Tutoriel = ID_Tutoriel;
        this.Niveau = Niveau;
        this.Title = Title;
        this.Description = Description;
        this.PathImg = PathImg;
        this.categorie = categorie;
        this.Creator = Creator;
    }

    public int getID_Tutoriel() {
        return ID_Tutoriel;
    }

    public void setID_Tutoriel(int ID_Tutoriel) {
        this.ID_Tutoriel = ID_Tutoriel;
    }

    public int getNiveau() {
        return Niveau;
    }

    public void setNiveau(int Niveau) {
        this.Niveau = Niveau;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPathImg() {
        return PathImg;
    }

    public void setPathImg(String PathImg) {
        this.PathImg = PathImg;
    }

    public Category getCategorie() {
        return categorie;
    }

    public void setCategorie(Category categorie) {
        this.categorie = categorie;
    }

    public AllUsers getCreator() {
        return Creator;
    }

    public void setCreator(AllUsers Creator) {
        this.Creator = Creator;
    }

    @Override
    public String toString() {
        return "Tutoriel{" + "ID_Tutoriel=" + ID_Tutoriel + ", Niveau=" + Niveau + ", Title=" + Title + ", Description=" + Description + ", PathImg=" + PathImg + ", categorie=" + categorie + ", Creator=" + Creator + '}';
    }

    


}
