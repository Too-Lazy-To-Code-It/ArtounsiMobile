/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author nour2
 */
public class MaConnexion {
    //db credentials 
    final String URL="jdbc:mysql://localhost:3306/pidev";//premier bib que je utioliseur = jdbc 
    final String USERNAME="root"; 
    final String PASSWORD ="";
     //var
    private Connection cnx;
    //1
     static  MaConnexion instance;
    
    public  MaConnexion(){
            
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public Connection getCnx() {
        return cnx;
    }

    //3
    public static MaConnexion getInstance() {
        if(instance == null)
            instance = new MaConnexion();
        
        return instance;
    }
    
    
    
}

      
    
    
