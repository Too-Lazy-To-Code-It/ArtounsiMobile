/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import util.MyConnection;
import models.Category;

import interfaces.CategoryInterface;
import util.MaConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author amine
 */
public class CategoryService implements CategoryInterface{
       //var
    Connection cnx = MyConnection.getInstance().getCnx();

    @Override
public void addCategory2(Category c) {
    try {
        // check if a category with the same name already exists
        String checkReq = "SELECT COUNT(*) FROM `category` WHERE `name_category`=?";
        PreparedStatement checkStmt = cnx.prepareStatement(checkReq);
        checkStmt.setString(1, c.getName_category());
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count > 0) {
            // a category with the same name already exists, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A category with the same name already exists!");
            alert.showAndWait();
            return;
        }

        // insert the new category
        String addReq = "INSERT INTO `category`(`name_category`) VALUES (?)";
        PreparedStatement addStmt = cnx.prepareStatement(addReq);
        addStmt.setString(1, c.getName_category());
        addStmt.executeUpdate();
        System.out.println("Category added successfully!");
        // display a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Category added successfully!");
        alert.showAndWait();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    
    @Override
    public void modifyCategory(Category c,String newName) {
        try {
        String req = "UPDATE `category` SET `name_category`=? WHERE `name_category`=?";
        PreparedStatement cat = cnx.prepareStatement(req);
        cat.setString(1, newName);
        cat.setString(2, c.getName_category());
        cat.executeUpdate();
        System.out.println("Category Modified Successfully!");
        } catch (SQLException ex) {
         ex.printStackTrace();
         }
    }
    
    
    @Override
public void deleteCategoryById(int id) {
    try {
        String req = "DELETE FROM category WHERE Id_Category = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Category deleted successfully!");
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    
    
    
    
    
    
    
       @Override
    public void deleteCategory(String name) {
        try {
                String req = "DELETE FROM category WHERE name_category = ?";
                PreparedStatement cat = cnx.prepareStatement(req);
                cat.setString(1, name);
                cat.executeUpdate();
                System.out.println("Category deleted successfully!");
                } catch (SQLException ex) {
                ex.printStackTrace();
}
    }
    
    
    @Override
    public List<Category> fetchCategories() {
         List<Category> categories = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM category";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                Category c = new Category();
                c.setId_Category(rs.getInt(1));
                c.setName_category(rs.getString(2));
                categories.add(c);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return categories;
    }

    //fetch by id

    @Override
    public Category fetchCategoryById(int id) {
        Category category = null;
        try {
            String req = "SELECT * FROM category WHERE id_Category=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = new Category();
                category.setId_Category(rs.getInt(1));
                category.setName_category(rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return category;
    }
    
//New
    @Override
    public Category fetchCategories(String name) {
        Category categorie = new Category();
        try {
            String req = "SELECT * FROM category WHERE name_category= '"+name+"'";
            PreparedStatement cat = cnx.prepareStatement(req);
            ResultSet rs = cat.executeQuery(req);
            while (rs.next()) {
                categorie.setId_Category(rs.getInt(1));
                categorie.setName_category(rs.getString(2));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categorie;
    }



    @Override
    public Category fetchCategoryByNom(String nom) {
      Category categorie = null;
        try {
            String req = "SELECT * FROM category WHERE name_Category=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categorie = new Category();
                categorie.setId_Category(rs.getInt(1));
                categorie.setName_category(rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categorie;
    }

   

   
}
