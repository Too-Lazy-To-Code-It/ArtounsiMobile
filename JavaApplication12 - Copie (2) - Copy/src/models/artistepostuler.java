/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author nour2
 */
public class artistepostuler {
   AllUsers idArtiste ;
  offreTravail idOffre;
int nbrpostu=0;

    public artistepostuler(  AllUsers idArtiste, offreTravail idOffre) {
        this.idArtiste = idArtiste;
        this.idOffre = idOffre;
    }

    public artistepostuler() {
       
    }

    public void setIdArtiste(  AllUsers idArtiste) {
        this.idArtiste = idArtiste;
    }

    public void setIdOffre(offreTravail idOffre) {
        this.idOffre = idOffre;
    }

    public void setNbrpostu(int nbrpostu) {
        this.nbrpostu = nbrpostu;
    }

    public   AllUsers getIdArtiste() {
        return idArtiste;
    }

    public offreTravail getIdOffre() {
        return idOffre;
    }

    public int getNbrpostu() {
        return nbrpostu;
    }

    @Override
    public String toString() {
         return "artistepostuler{" + "idArtiste=" + idArtiste + ", idOffre=" + idOffre + ", nbrpostu=" + nbrpostu + '}';
    }
   
    

    

  
}
