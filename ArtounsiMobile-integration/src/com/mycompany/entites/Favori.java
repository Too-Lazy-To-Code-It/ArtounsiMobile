/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

/**
 *
 * @author achref
 */
public class Favori {
    int id_favoris;

    public int getId_favoris() {
        return id_favoris;
    }

    public void setId_favoris(int id_favoris) {
        this.id_favoris = id_favoris;
    }

    @Override
    public String toString() {
        return "Favori{" + "id_favoris=" + id_favoris + '}';
    }
    
}
