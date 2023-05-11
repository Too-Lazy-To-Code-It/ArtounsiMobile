/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entity.User;
import java.util.List;

/**
 *
 * @author amine
 */
public interface UserInterface {
    //add
    public void addUser(User u);
    public void addUser2(User u);
    
    //list : select
    public List<User> fetchUsers();
}
