/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import interfaces.ViewInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import models.Logged;
import models.Tutoriel;
import models.Video;
import util.MaConnexion;

/**
 *
 * @author achref
 */
public class ViewService implements ViewInterface {

    Connection cnx = MaConnexion.getInstance().getCnx();
    
    @Override
    public void addVue(Video v) {
                try {
            if(!vued(v))
            {
                String req = "INSERT INTO `view`(`id_user`,`id_video`) VALUES (?,?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setDouble(1, Logged.get_instance().getUser().getID_User());
                ps.setInt(2, v.getID_Video());
                ps.executeUpdate();
                System.out.println("rate is inserted");
            }
            else
            {
                String req = "update view set `date_v`=? where `id_user`= ? and `id_video`=? ";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setTimestamp(1, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                ps.setInt(2, Logged.get_instance().getUser().getID_User());
                ps.setInt(3, v.getID_Video());
                ps.executeUpdate();
                System.out.println("rate is updated");

            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        
    }

    @Override
    public int fetchNumViewsByVideo(Video v) {
        try {
            
            String req = "SELECT count(*) as n FROM view WHERE id_video="+v.getID_Video();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                return rs.getInt("n");
            }   
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public List<Video> fetchHistory() {
        List<Video> videos = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM video as v,tutoriel as t,view as vi where v.ID_Tutoriel=t.ID_Tutoriel and vi.id_video=v.ID_Video and vi.id_user="+Logged.get_instance().getUser().getID_User()+" Order By(date_v) DESC";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Video v = new Video();
                Tutoriel t = new Tutoriel();

                v.setID_Video(rs.getInt("ID_Video"));
                v.setTitle(rs.getString("Title"));
                v.setDate_p(rs.getString("Date_p"));
                v.setDescrption(rs.getString("Description"));
                v.setPathImage(rs.getString("pathVideo"));
                v.setPathImage(rs.getString("pathImage"));
                
                t.setID_Tutoriel(rs.getInt("ID_Tutoriel"));
                t.setTitle(rs.getString("Title"));
                t.setNiveau(rs.getInt("Niveau"));
                t.setDescription(rs.getString("Description"));
                
                v.setTutoriel(t);
                videos.add(v);
            }   
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return videos;
    }

    @Override
    public boolean vued(Video v) {
        try {
            
            String req = "SELECT * FROM view as v where v.id_video="+v.getID_Video()+" and v.id_user="+Logged.get_instance().getUser().getID_User();
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
    
    
    
}
