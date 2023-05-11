/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author amine
 */
public class User {
       //att
    private int id;
    private String name;
    private int number, age;

    //const

    public User() {
    }
    
    
    
    public User(int id, String name, int number, int age) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.age = age;
    }

    public User(String name, int number, int age) {
        this.name = name;
        this.number = number;
        this.age = age;
    }

    public User(int i, String username) {
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
    //display

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", number=" + number + ", age=" + age + '}';
    }
}
