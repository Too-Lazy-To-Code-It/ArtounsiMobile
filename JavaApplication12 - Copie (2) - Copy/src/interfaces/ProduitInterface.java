/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import models.Category;
import models.Panier;
import models.Produits;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author aouad
 */
public interface ProduitInterface {
    //add
    public void addProduit(Produits p);
    //list : select
    public List<Produits> fetchProduits();
// //supprimer
//    public void  spprimerProduit(String nom); 
//    public void spprimerProduit(Produits p);
  public void spprimerProduit(int idProduit);
//    //modifier

  public void modifierProduit(Produits p);
//   
//   //afficher
   
//   public void afficherProduit(Produits p, int id);
  
//chercher par nom
public ObservableList<Produits> chercherProduitParCateg(String nomCategorie);

//   public Produits chercherProduitParNom(String nom);
 public ObservableList<Produits> chercherProduitParNom(String nom);
public void chercherProduitParCategorie(Category CategorieProduit);
    public Produits readById(int id);
    public Produits readByName(String nom);
    

   

   



   

    
 

}
