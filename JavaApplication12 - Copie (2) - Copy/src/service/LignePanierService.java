/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import interfaces.LignePanierInterface;
import models.LignePanier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.MaConnexion;

/**
 *
 * @author aouad
 */
public class LignePanierService implements LignePanierInterface{
     //var
    Connection cnx = MaConnexion.getInstance().getCnx();
    
   //var 
   

//******************************* Ajouter une ligne Panier  ***********************************************//      
    @Override
    public void ajouterLignePanier(LignePanier lp) {
       
        try {
            Date date = new Date();
            java.sql.Timestamp  sqldate  = new java.sql.Timestamp(date.getTime());
            
            String req ="INSERT INTO `lignepanier`(`idpanier`,`idproduit`,`dateAjout`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, lp.getPanier().getIdpanier());
            ps.setInt(2, lp.getProduit().getIdproduit());
            ps.setString(3, lp.getProduit().getNom());
            ps.setString(4, lp.getProduit().getImage());
            ps.setDouble(5, lp.getProduit().getPrix());
            ps.setTimestamp(6,sqldate);
            ps.executeUpdate();
            System.out.println("LignePanier ajoutée avec succés!");
            
        } catch (SQLException ex) {
            ex.printStackTrace();}
    
    }


 
    
 //******************************* supprimer ligne panier  ***********************************************//      
   @Override
    public void supprimerLignePanier(int idlignepanier  ){
      try {
            String req = "DELETE FROM lignepanier WHERE idlignepanier = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,idlignepanier);
            ps.executeUpdate();
            System.out.println("Ligne Panier supprimée avec succés!");
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    
//******************************* Afficher  lignePanier  ***********************************************//  
    
 @Override
public ArrayList<LignePanier> afficherTous() {
    ArrayList<LignePanier> lignepanier = new ArrayList<>();
    ProduitService prodserv = new ProduitService();
    PanierService panserv = new PanierService();
    try {
        String req ="SELECT * FROM lignepanier";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LignePanier lignep = new LignePanier();
            lignep.setIdlignepanier(rs.getInt("idlignepanier"));
            lignep.setPanier(panserv.afficherPanierParId(rs.getInt("idpanier")));
            lignep.setProduit(prodserv.readById(rs.getInt("idproduit")));

            lignep.setDateajout(rs.getTimestamp("dateajout"));
         
            System.out.println();
            lignepanier.add(lignep);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return lignepanier;
}

//******************************* Afficher  ligne***********************************************//  
    @Override
    public List<LignePanier> afficheligne(int idpanier) {
        ProduitService prodserv = new ProduitService();
            ArrayList<LignePanier> lignepanier;
            lignepanier = new ArrayList<>();
        
    try {   
          
           String req = "SELECT  *  FROM lignepanier WHERE idpanier = ?";
           PreparedStatement ps = cnx.prepareStatement(req);
           ps.setInt(1, idpanier);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {{
                    LignePanier lignep = new LignePanier();
                    int idproduit = rs.getInt("idproduit");
                    lignep.setProduit(prodserv.readById(lignep.getProduit().getIdproduit()));
                    lignep.setDateajout(rs.getTimestamp("dateajout"));
   
                lignepanier.add(lignep);
                    }   }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération des lignes de panier: " + ex.getMessage());
    }
    return lignepanier;
}
     
     
//******************************* Afficher  lignePanier  par id ***********************************************//  
    @Override
    public List<LignePanier> getLignePanierparIdPanier(int idpanier) {
        ProduitService pr= new ProduitService();
            ArrayList<LignePanier> lignepanier;
            lignepanier = new ArrayList<>();
          ProduitService prodserv = new ProduitService();
          PanierService panserv = new PanierService();
    try {   
          
           String req = "SELECT * FROM lignepanier WHERE idpanier = ?";
           PreparedStatement ps = cnx.prepareStatement(req);
           ps.setInt(1, idpanier);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {{
                    LignePanier lignep = new LignePanier();
                    lignep.setProduit(pr.readById(lignep.getProduit().getIdproduit()));
                    lignep.setDateajout(rs.getTimestamp("dateajout"));
                        
                  System.out.println();
                lignepanier.add(lignep);
                    }   }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération des lignes de panier: " + ex.getMessage());
    }
    return lignepanier;
}
     
    @Override
    public LignePanier getLignePanierparIdLignePanier(int idlignepanier) {
        ProduitService pr= new ProduitService();
        LignePanier lignePanier = null;
    try {
        String req = "SELECT * FROM lignepanier WHERE idlignepanier = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idlignepanier);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            lignePanier = new LignePanier();
            lignePanier.setIdlignepanier(rs.getInt("idlignepanier"));
            lignePanier.getPanier().setIdpanier(rs.getInt("idpanier"));
            lignePanier.getProduit().setIdproduit(rs.getInt("idproduit"));
            lignePanier.setProduit(pr.readById(lignePanier.getProduit().getIdproduit()));
            lignePanier.setDateajout(rs.getDate("dateajout"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
         return lignePanier;
}
    
    
    
    

      
}
