/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import models.LignePanier;
import models.Panier;
import java.util.List;

/**
 *
 * @author aouad
 */
public interface PanierInterface {
     //list : select
    List<Panier> fetchPanier();
//    void afficherProduit(Panier pan,int idPanier);

     public void ajouterPanier(Panier pan);
     public void modifierPanier(Panier pan, int IdPanier);
     public void supprimerPanier(int idPanier);
     public void viderPanier();
     public Panier afficherPanierParId(int idPanier);
     public double calculerMontantTotal(int idPanier);
//     public void MisàjourMontantTotal(Panier pan,int idPanier,double montant_tot );
      public void MisàjourMontantTotal(int idPanier,double montant_tot );
     public int calculerNombreProduits(int idPanier);
    
}
