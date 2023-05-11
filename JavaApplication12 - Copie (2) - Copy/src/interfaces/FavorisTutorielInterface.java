/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import models.FavorisTutorial;
import models.Tutoriel;

/**
 *
 * @author achref
 */
public interface FavorisTutorielInterface {
    public void addFavoris(Tutoriel t);
    public void removeFavoris(Tutoriel t);
    public boolean favorated(Tutoriel t);
    public List<Tutoriel> fetchFavorisTutorials();
}
