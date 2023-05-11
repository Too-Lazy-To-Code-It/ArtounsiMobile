/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import models.Tutoriel;
import java.sql.Connection;
import java.sql.Statement;
import interfaces.TutorielInterface;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AllUsers;
import models.Category;
import util.MaConnexion;

/**
 *
 * @author achref
 */
//on utilise htdocs
public class TutorielService implements TutorielInterface {

    Connection cnx = MaConnexion.getInstance().getCnx();

    public void addTutoriel(Tutoriel t) {
        try {
            String req = "INSERT INTO `tutoriel`(`Title`,`Niveau`,`Description`, `PathImg`, `ID_Categorie`, `ID_Artist`) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, t.getTitle());
            ps.setInt(2, t.getNiveau());
            ps.setString(3, t.getDescription());
            ps.setString(4, t.getPathImg());
            ps.setInt(5, t.getCategorie().getId_Category());
            ps.setInt(6, t.getCreator().getID_User());
            ps.executeUpdate();
            System.out.println("Tutoriel Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void ModifyTutoriel(Tutoriel t) {
        try {
            String req = "update Tutoriel set `Title`=?, `Niveau`=?,`Description`=?,`PathImg`=? where `ID_Tutoriel`= ? ";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getTitle());
            ps.setInt(2, t.getNiveau());
            ps.setString(3, t.getDescription());
            ps.setString(4, t.getPathImg());
            ps.setInt(5, t.getID_Tutoriel());
            ps.executeUpdate();
            System.out.println("Tutoriel Modified Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTutoriel(int id) {
        String req = "DELETE FROM tutoriel where ID_Tutoriel = " + id;
        Statement st;
        try {
            st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Tutoriel deleted Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public List<Tutoriel> fetchTutoriels() {
        List<Tutoriel> tutoriels = new ArrayList<>();
        try {

            String req = "SELECT * FROM tutoriel as t,category as c,AllUsers as u where t.ID_Categorie=c.id_category and t.ID_Artist=u.ID_User";
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

    @Override
    public List<Tutoriel> fetchTutorielsByArtist(String NomArtist) {
        List<Tutoriel> tutoriels = new ArrayList<>();
        try {

            String req = "SELECT * FROM tutoriel as t,category as c,AllUsers as u where t.ID_Categorie=c.id_category and t.ID_Artist=u.ID_User and u.Name = '" + NomArtist + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Tutoriel t = new Tutoriel();
                Category c = new Category();

                t.setID_Tutoriel(rs.getInt(1));

                t.setID_Tutoriel(rs.getInt("ID_Tutoriel"));
                t.setTitle(rs.getString("Title"));
                t.setNiveau(rs.getInt("Niveau"));
                t.setDescription(rs.getString("Description"));
                t.setPathImg(rs.getString("PathImg"));

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

    @Override
    public List<Tutoriel> fetchTutorielsByTitle(String title) {
        List<Tutoriel> tutoriels = new ArrayList<>();
        try {

            String req = "SELECT * FROM tutoriel as t,category as c,AllUsers as u where t.ID_Categorie=c.id_category and t.ID_Artist=u.ID_User and t.Title like '%" + title + "%'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Tutoriel t = new Tutoriel();
                Category c = new Category();

                t.setID_Tutoriel(rs.getInt(1));

                t.setID_Tutoriel(rs.getInt("ID_Tutoriel"));
                t.setTitle(rs.getString("Title"));
                t.setNiveau(rs.getInt("Niveau"));
                t.setDescription(rs.getString("Description"));
                t.setPathImg(rs.getString("PathImg"));

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
        System.out.println(tutoriels);
        return tutoriels;
    }

    @Override
    public Tutoriel fetchTutorielByID(int ID_Tutoriel) {
        Tutoriel t = new Tutoriel();
        try {

            String req = "SELECT * FROM tutoriel as t,category as c,AllUsers as u where t.ID_Categorie=c.id_category and t.ID_Artist=u.ID_User and t.ID_Tutoriel = " + ID_Tutoriel;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Category c = new Category();

                t.setID_Tutoriel(rs.getInt("ID_Tutoriel"));
                t.setTitle(rs.getString("Title"));
                t.setNiveau(rs.getInt("Niveau"));
                t.setDescription(rs.getString("Description"));
                t.setPathImg(rs.getString("PathImg"));

                c.setId_Category(rs.getInt("id_category"));
                c.setName_category(rs.getString("name_category"));

                AllUsers u = new AllUsers();
                u.setName(rs.getString("Name"));
                u.setID_User(rs.getInt("ID_user"));
                u.setType(rs.getString("Type"));

                t.setCreator(u);
                t.setCategorie(c);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return t;
    }

    @Override
    public List<Tutoriel> fetchTutorielsByCategorie(String name) {
        List<Tutoriel> tutoriels = new ArrayList<>();
        try {
            String req = "SELECT * FROM tutoriel as t,category as c,AllUsers as u where t.ID_Categorie=c.id_category and t.ID_Artist=u.ID_User and t.ID_Categorie= ANY(select id_category from categorie2 where name_category in " + name + ")";;
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
