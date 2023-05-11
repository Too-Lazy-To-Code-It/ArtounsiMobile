/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

/**
 *
 * @author amine
 */
public class grosmot {
     int idMot;
     String  mot;

    public grosmot(String mot) {
        this.mot = mot;
    }

    public grosmot(int idMot, String mot) {
        this.idMot = idMot;
        this.mot = mot;
    }

    public grosmot() {
   
    }

    public int getIdMot() {
        return idMot;
    }

    public String getMot() {
        return mot;
    }

    public void setIdMot(int idMot) {
        this.idMot = idMot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    @Override
    public String toString() {
        return "grosmot{" + "idMot=" + idMot + ", mot=" + mot + '}';
    }
     
    
}
