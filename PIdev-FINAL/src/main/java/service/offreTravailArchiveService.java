/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import models.AllUsers;
import interfaces.offreTravailArchiveInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import models.Category;

import models.offreTravailarchive;
import util.MaConnexion;

/**
 *
 * @author nour2
 */
public class offreTravailArchiveService implements offreTravailArchiveInterface {

    Connection cnx = MaConnexion.getInstance().getCnx();
   AllUsers studio = new  AllUsers();
//*******************recuperer l'offre archiver*****************************

    @Override
    public void recupererOffre(offreTravailarchive o) {
        try {
//ajouter la date de recuperation***********************************
            Date date = new Date();
            Timestamp sqldate = new java.sql.Timestamp(date.getTime());
            // inserer loffre une autre fois dans la table offretravail*****************************
             String req2 = "INSERT INTO `offreTravail2`(`ID_User`,`Nickname`,`titreOffre`, `descriptionOffre`, `categorieOffre`,`dateAjoutOffre`,`typeOffre`,`localisationOffre`,`idCategorie`) VALUES (?,?,?,?,?,?,?,?,?)";

                    PreparedStatement ps = cnx.prepareStatement(req2);
                    ps.setInt(1, o.getIdStudio());
                    ps.setString(2, o.getNomStudio());
                    ps.setString(3, o.getTitreOffre());
                    ps.setString(4, o.getDescriptionOffre());
                    ps.setString(5, o.getCategorieOffre().getName_category());
                    ps.setString(7, o.getTypeOffre());
                    ps.setString(8, o.getLocalisationOffre());
                    ps.setInt(9, o.getCategorieOffre().getId_Category());

                    ps.setTimestamp(6, sqldate);
                    ps.executeUpdate();
            Statement st = cnx.createStatement();
            //finallement supprimer l'offre de la table d'archive************************
            String req = "DELETE FROM `offretravailarchive2` WHERE idOffre= " + o.getIdOffre() + "";
            st.executeUpdate(req);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("votre offre est recuperer");

            alert.showAndWait();

        } catch (SQLException ex) {
            Logger.getLogger(offreTravailArchiveService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
//******************supprimer l'offre definitivement*********************************
    @Override
    public void SupprimerDefinitivement(offreTravailarchive o) {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM `offretravailarchive2` WHERE idOffre= " + o.getIdOffre() + "";
            st.executeUpdate(req);
            System.out.println("Offre de travail supprimer avec succès");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("offre de travail supprimer avec succès");

            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(offreTravailArchiveService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //**********afficher les offres de travail archiver d'utilisateur connecté**************************************
    @Override
    public List<offreTravailarchive> fetchOffresarchiverPerIdDate(int id) {
        List<offreTravailarchive> offresTravail = new ArrayList<>();

        try {

            String req = "SELECT * FROM `offretravailArchive2` WHERE ID_User= \'" + id + "\' ORDER BY dateAjoutOffre DESC";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                offreTravailarchive of = new offreTravailarchive();

              Category c = new Category();
                of.setIdOffre(rs.getInt("idOffre"));
                of.setIdStudio(rs.getInt(1));
                of.setNomStudio(rs.getString(5));
                of.setTitreOffre(rs.getString(2));
                of.setDescriptionOffre(rs.getString(3));

                c.setId_Category(rs.getInt("idCategorie"));
                c.setName_category(rs.getString("categorieOffre"));
                of.setCategorieOffre(c);
                of.setDateAjoutOffre(rs.getTimestamp(6));
                of.setTypeOffre(rs.getString(7));
                of.setLocalisationOffre(rs.getString(8));
                offresTravail.add(of);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return offresTravail;

    }

    //***********************afficher loffre par son id *************************
    @Override
    public offreTravailarchive fetchOffrearchiveParId(int id) {
        offreTravailarchive of = new offreTravailarchive();
        try {

            String req = "SELECT * FROM `offretravailarchive2` WHERE idOffre= \'" + id + "\' ";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                of.setIdOffre(rs.getInt(10));
                of.setTitreOffre(rs.getString(2));
                of.setDescriptionOffre(rs.getString(3));

                Category c = new Category(rs.getInt(9), rs.getString(4));
                of.setIdStudio(rs.getInt(1));
                of.setNomStudio(rs.getString(5));
                of.setCategorieOffre(c);
                of.setDateAjoutOffre(rs.getTimestamp(6));
                String categorieinfos = c.toString();
                of.setTypeOffre(rs.getString(7));
                of.setLocalisationOffre(rs.getString(8));

            }
        } catch (SQLException ex) {
            Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return of;
    }
}
