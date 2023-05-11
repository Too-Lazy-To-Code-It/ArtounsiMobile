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
public class grosMots {
   int idmot ; 
   String mot;

    public grosMots() {
    }

    public grosMots(int idmot, String mot) {
        this.idmot = idmot;
        this.mot = mot;
    }

    public int getIdmot() {
        return idmot;
    }

    public String getMot() {
        return mot;
    }

    public void setIdmot(int idmot) {
        this.idmot = idmot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    @Override
    public String toString() {
        return "grosMots{" + "idmot=" + idmot + ", mot=" + mot + '}';
       
    }
    public String toStringmot() {
        return mot ;
    }
}
