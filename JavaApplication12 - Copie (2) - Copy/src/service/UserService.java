/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.User;
import Interfaces.UserInterface;
import util.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amine
 */
public class UserService implements UserInterface{
    //var
    Connection cnx = MyConnection.getInstance().getCnx();
    @Override
    public void addUser(User u) {
        try {
            String req = "INSERT INTO `user`(`name`, `number`, `age`) VALUES ('"+ u.getName() +"',"+u.getNumber()+","+u.getAge()+")";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user Added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addUser2(User u) {
         try {
            
            String req = "INSERT INTO `user`(`name`, `number`, `age`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getName());
            ps.setInt(2, u.getNumber());
            ps.setInt(3, u.getAge());
            ps.executeUpdate();
            System.out.println("user Added Successfully!");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> fetchUsers() {
        List<User> users = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM user";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                User u = new User();
                u.setId(rs.getInt(1));
                u.setName(rs.getString(2));
                u.setNumber(rs.getInt("number"));
                u.setAge(rs.getInt("age"));
                
                users.add(u);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return users;
    }
    
}
