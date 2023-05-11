/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import models.LignePanier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aouad
 */
public interface LignePanierInterface {
    public void ajouterLignePanier(LignePanier lp);
    


//     public void supprimerLignePanier(int idLignePanier);
//      public void supprimerLignePanier(int idPanier, int idProduit);
//       public void supprimerLignePanier(int idLignePanier);
//       public void supprimerLignePanier(int idPanier, int idProduit);
    public void supprimerLignePanier(int idLignePanier);
//         public void supprimerLignePanier(int idPanier, int idProduit);
     public List<LignePanier> afficheligne(int idPanier);
     //list : select
   public ArrayList<LignePanier> afficherTous();
    public List<LignePanier> getLignePanierparIdPanier(int idPanier);
    
    public LignePanier getLignePanierparIdLignePanier(int idLignePanier) ;
}
