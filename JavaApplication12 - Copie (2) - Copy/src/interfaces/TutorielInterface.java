/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import models.Tutoriel;
import java.util.List;

/**
 *
 * @author achref
 */
public interface TutorielInterface {
    
    //ajout
    public void addTutoriel(Tutoriel t);
    
    //modification
    public void ModifyTutoriel(Tutoriel t);
    
    //suppression
    public void deleteTutoriel(int id);
    
    
    //list : select
    public List<Tutoriel> fetchTutoriels();
    
    public Tutoriel fetchTutorielByID(int ID_Tutoriel);
    
    public List<Tutoriel> fetchTutorielsByCategorie(String name);
    
    public List<Tutoriel> fetchTutorielsByArtist(String NomArtist);
    
    public List<Tutoriel> fetchTutorielsByTitle(String title);
    //affectation
    //public void affecterJoueur(Player p, Team t);
    
}

