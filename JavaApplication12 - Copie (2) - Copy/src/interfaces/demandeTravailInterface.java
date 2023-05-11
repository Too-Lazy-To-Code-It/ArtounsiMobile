package interfaces;

import java.util.List;
import models.Category;
import models.demandeTravail;
import models.offreTravail;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nour2
 */
public interface demandeTravailInterface {

    public void addDemande(demandeTravail d);
    //afficher list

    public List<demandeTravail> fetchDemandesPerDate();
//afficher list par id connected

    public List<demandeTravail> fetchDemandesPerIdDate(int id);

    public void modifierDemande(demandeTravail d);

    public void SupprimerDemande(demandeTravail d);

    public List<demandeTravail> chercherDemande(String mots);

    public void contacterViaMail(int idStudio, demandeTravail d);

    public demandeTravail fetchdemandeParId(int id);

    public List<demandeTravail> offresimilairaunedemande(int id);
    public List<demandeTravail> fetchDemandesPerCategorieDate( List<demandeTravail>d,Category c);
}
