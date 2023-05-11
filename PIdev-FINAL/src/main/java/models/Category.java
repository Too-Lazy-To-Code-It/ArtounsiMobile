/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author amine
 */
public class Category {
    //att
    private int Id_Category;
    private String name_category;

    public Category() {
    }

    public Category(int Id_Category, String name_category) {
        this.Id_Category = Id_Category;
        this.name_category = name_category;
    }

    public int getId_Category() {
        return Id_Category;
    }

    public void setId_Category(int Id_Category) {
        this.Id_Category = Id_Category;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    @Override
    public String toString() {
        return "Category{" + "Id_Category=" + Id_Category + ", name_category=" + name_category + '}';
    }
}
