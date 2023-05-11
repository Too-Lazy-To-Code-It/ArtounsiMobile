/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import models.Challenge;
import models.Participation;

import java.util.List;

/**
 *
 * @author achref
 */
public interface ParticipationInterface {
    
    public void addParticipation(Participation p);
    
    //suppression
    public void deleteParticipation(int ID_Challenge, int ID_Participant);
    
    //list : select
    public List<Participation> fetchParticipations();
    
    public List<Participation> fetchParticipantionsByChallenge(int ID_Challenge);

    public List<Challenge> fetchChallengesByParticipant(int ID_Participant);
    public Boolean Participated(int ID_Challenge, int ID_Participant);
    
}
