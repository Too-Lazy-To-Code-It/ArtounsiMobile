/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import interfaces.grosMotsInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

import models.grosMots;

import util.MaConnexion;

/**
 *
 * @author nour2
 */
public class grosMotsService implements  grosMotsInterface {
     Connection cnx = MaConnexion.getInstance().getCnx();
        //******************** suppression*********************************************

    public void Supprimer(grosMots o) {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM `grosmots2` WHERE idMot= " + o.getIdmot() + "";
            st.executeUpdate(req);
            System.out.println("gros mot supprimer avec succès");
            
                   } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }}
    //******************** affichage*********************************************
         public List<grosMots> fetchgrosmots() {
              List<grosMots> grosmots = new ArrayList<>();
         try {
            
             String req = "SELECT * FROM `grosmots2` ";
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req);
             while (rs.next()) {
                 grosMots grosmot= new grosMots();
                 grosmot.setIdmot(rs.getInt(1));
                 grosmot.setMot(rs.getString(2));
                 grosmots.add(grosmot);
             } 
         } catch (SQLException ex) {
             Logger.getLogger(grosMotsService.class.getName()).log(Level.SEVERE, null, ex);
         }
return  grosmots;
    }
    public List<String> fetchgrosmotsString() {
              List<String> grosmots = new ArrayList<>();
         try {
            
             String req = "SELECT * FROM `grosmots2` ";
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req);
             while (rs.next()) {
                 grosMots grosmot= new grosMots();
                 grosmot.setIdmot(rs.getInt(1));
                 grosmot.setMot(rs.getString(2));
                                   grosmots.add( grosmot.toStringmot());

             } 
         } catch (SQLException ex) {
             Logger.getLogger(grosMotsService.class.getName()).log(Level.SEVERE, null, ex);
         }
return  grosmots;
    }
    //******************** ajout*********************************************
             public void ajoutGrosMot(grosMots mot) {
        try {
            if(mot.getMot().trim().length() == 0) { Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Attention, le champs est  vide");}
            else if(mot.getMot().matches("\\d+")){ 
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
                    alert.setHeaderText(null);
                            
                    alert.setContentText("Attention, entre un mot valide!");}
            else{
            String req = "INSERT INTO `grosmots2` (`mot`) VALUES (?)";
            PreparedStatement cat = cnx.prepareStatement(req);
            cat.setString(1, mot.getMot());
            cat.executeUpdate();
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("gros mot ajouté");

                    alert.showAndWait();
            System.out.println("gros mot ajouté");}
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
             //********************modification*********************************************


    public static boolean verifmot=false;

public void modifierOffre(grosMots mot) {
         try {
             
             //verifier si l'offre est deja ajouter par le studio conencter ou non en collecton les offre qui l  id studio et titre doffre
             String req3 = "SELECT COUNT(*)FROM `grosmots2` WHERE  mot=\'" + mot.getMot() + "\'";
             Statement st3 = cnx.createStatement();
             ResultSet rs3 = st3.executeQuery(req3);
             if (rs3.next() && rs3.getInt(1) > 1) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Alert");
                 alert.setHeaderText(null);
                 alert.setContentText("nom existe");
                 
                 alert.showAndWait();
                 
                 
                 
             } else   if(mot.getMot().trim().length() == 0) { Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Attention, le champs est  vide");}
            else if(mot.getMot().matches("\\d+")){ 
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
                    alert.setHeaderText(null);
                            
                    alert.setContentText("Attention, entre un mot valide!");}
            else
             {
                 String req = "update `grosmots2` set mot=? where idMOT=?";
                 PreparedStatement pst = cnx.prepareStatement(req);
                 pst.setString(1, mot.getMot());
                 pst.setInt(2, mot.getIdmot());
                 pst.executeUpdate();
                 
                 System.out.println("gros mot modifié  avec succès");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("gros mot de travail modifié  avec succès");
               verifmot=true;
                    alert.showAndWait();
                 
             } 
         } catch (SQLException ex) {
             Logger.getLogger(grosMotsService.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
 @Override
    public grosMots fetchmotById(int id) {
        grosMots mot = null;
        try {
            String req = "SELECT * FROM grosmots2 WHERE idMot=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               mot= new  grosMots();
               mot.setIdmot(rs.getInt(1));
               mot.setMot(rs.getString(2));
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mot;
    }
}
