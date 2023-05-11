/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;

import models.grosMots;


/**
 *
 * @author nour2
 */
public interface grosMotsInterface {
       public void ajoutGrosMot(grosMots mot) ;
    //afficher list
 public List<String> fetchgrosmotsString();

public void modifierOffre(grosMots mot);
public void Supprimer(grosMots o);
  public List<grosMots> fetchgrosmots() ;
  public grosMots fetchmotById(int id);
    
}
