/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import models.offreTravail;
import models.offreTravailarchive;

/**
 *
 * @author nour2
 */
 public interface offreTravailArchiveInterface {
      public void recupererOffre(offreTravailarchive o );
      public void SupprimerDefinitivement(offreTravailarchive o);
       public List<offreTravailarchive> fetchOffresarchiverPerIdDate(int id);
           public offreTravailarchive fetchOffrearchiveParId(int id) ;
}
