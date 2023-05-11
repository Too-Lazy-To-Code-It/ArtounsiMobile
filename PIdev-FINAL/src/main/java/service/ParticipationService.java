/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import models.Challenge;
import models.Participation;
import interfaces.ParticipationInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AllUsers;
import util.MaConnexion;

/**
 *
 * @author achref
 */
public class ParticipationService implements ParticipationInterface {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void addParticipation(Participation p) {
         try {
            
            String req = "INSERT INTO Participation( `ID_Challenge`, `ID_User`, `IMG_Participation`, `Description`) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getChallenge().getID_Challenge());
            ps.setInt(2, p.getParticipant().getID_User());
            ps.setString(3, p.getIMG_Participation());
            ps.setString(4, p.getDescription());

            ps.executeUpdate();
            System.out.println("Participation Added Successfully!");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteParticipation(int ID_Challenge, int ID_Participant) {
        String req = "DELETE FROM participation where ID_Challenge = "+ID_Challenge+" AND ID_User = "+ID_Participant;
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
    public List<Participation> fetchParticipations() {
            List<Participation> Participations = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM participation p, challenge c, utilisateur u where p.ID_User=u.ID_user AND p.ID_Challenge=c.ID_Challenge";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) { 
                Participation p = new Participation();
                
                p.setIMG_Participation(rs.getString("IMG_Participation"));
                p.setDescription(rs.getString("Description"));
                
                AllUsers u = new AllUsers();
                u.setName(rs.getString("Name"));
                u.setID_User(rs.getInt("ID_user"));
                
                Challenge c = new Challenge();
                c.setID_Challenge(rs.getInt("ID_Challenge"));
                c.setTitle(rs.getString("Title"));
                c.setDescription(rs.getString("Description"));
                c.setDate_C(rs.getString("Date_C"));
                c.setPathIMG(rs.getString("PathIMG"));
                c.setNiveau(rs.getInt("Niveau"));
                
                p.setParticipant(u);
                p.setChallenge(c);
                Participations.add(p);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Participations;    
    }
    
        @Override
    public List<Participation> fetchParticipantionsByChallenge(int ID_Challenge) {
        List<Participation> Participations = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM participation where ID_Challenge = "+ID_Challenge;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                Participation p = new Participation();
                
                p.setIMG_Participation(rs.getString("IMG_Participation"));
                p.setDescription(rs.getString("Description"));
                
                AllUsers u = new AllUsers();
                u.setID_User(rs.getInt("ID_user"));
                
                Challenge c = new Challenge();
                c.setID_Challenge(rs.getInt("ID_Challenge"));
                
                p.setParticipant(u);
                p.setChallenge(c);
                
                Participations.add(p);
                System.out.println("pp: "+p);
                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Participations;    
    }

    @Override
    public List<Challenge> fetchChallengesByParticipant(int ID_Participant) {
                List<Challenge> Challenges = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM participation p, challenge c where p.ID_Challenge = c.ID_Challenge AND p.ID_User = "+ID_Participant;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                Challenge c = new Challenge();
                c.setID_Challenge(rs.getInt("ID_Challenge"));
                c.setTitle(rs.getString("Title"));
                c.setDescription(rs.getString("Description"));
                c.setDate_C(rs.getString("Date_C"));
                c.setPathIMG(rs.getString("PathIMG"));
                c.setNiveau(rs.getInt("Niveau"));
                Challenges.add(c);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Challenges;    
    }

    @Override
    public Boolean Participated(int ID_Challenge, int ID_Participant) {
        int id = 0;
        try {
            String req = "SELECT * FROM participation where ID_Challenge = "+ID_Challenge+" and ID_User = "+ID_Participant;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {                
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            return false;
    }
}
