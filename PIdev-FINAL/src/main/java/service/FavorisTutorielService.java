/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.AllUsers;
import models.Category;
import models.FavorisTutorial;
import models.Logged;
import models.Tutoriel;
import util.MaConnexion;
import interfaces.FavorisTutorielInterface;

/**
 *
 * @author achref
 */
public class FavorisTutorielService implements FavorisTutorielInterface {
        Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void addFavoris(Tutoriel t) {
        try {
            String req = "INSERT INTO `favoris_turoial`(`id_user`,`id_tutoriel`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);

            // Set the values for the statement
            ps.setInt(1, Logged.get_instance().getUser().getID_User());
            ps.setInt(2, t.getID_Tutoriel());
            
            ps.executeUpdate();
            System.out.println("Favoris Added Successfully!");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void removeFavoris(Tutoriel t) {
        String req = "DELETE FROM `favoris_turoial` where id_tutoriel = "+t.getID_Tutoriel()+" and id_user = "+Logged.get_instance().getUser().getID_User();
        Statement st;
        try {
            st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Video deleted Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean favorated(Tutoriel t) {
        try {
            
            String req = "SELECT * FROM favoris_turoial as ft where ft.id_user="+Logged.get_instance().getUser().getID_User()+" and ft.id_tutoriel="+t.getID_Tutoriel();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                return true;
            }   
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Tutoriel> fetchFavorisTutorials() {
        List<Tutoriel> tutoriels = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM favoris_turoial as ft,tutoriel as t,category as c,AllUsers as u where t.ID_Categorie=c.id_category and t.ID_Artist=u.ID_User and ft.id_user="+Logged.get_instance().getUser().getID_User()+" and ft.id_tutoriel= t.ID_Tutoriel";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                Tutoriel t = new Tutoriel();

                t.setID_Tutoriel(rs.getInt(1));

                t.setID_Tutoriel(rs.getInt("ID_Tutoriel"));
                t.setTitle(rs.getString("Title"));
                t.setNiveau(rs.getInt("Niveau"));
                t.setDescription(rs.getString("Description"));
                t.setPathImg(rs.getString("PathImg"));
                
                Category c = new Category();
                c.setId_Category(rs.getInt("id_category"));
                c.setName_category(rs.getString("name_category"));
            
                AllUsers u = new AllUsers();
                u.setName(rs.getString("Name"));
                u.setID_User(rs.getInt("ID_user"));
                u.setType(rs.getString("Type"));
                
                t.setCreator(u);
                t.setCategorie(c);
                tutoriels.add(t);
            }   
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return tutoriels;
    }
    
}
