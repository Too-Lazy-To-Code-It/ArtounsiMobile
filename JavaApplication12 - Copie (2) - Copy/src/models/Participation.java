/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author achref
 */
public class Participation {
    private AllUsers participant;
    private Challenge challenge;
    private String IMG_Participation;
    private String Description;
    private int Id_Participation;

    public Participation() {
    }

    public Participation(AllUsers participant, Challenge challenge, String IMG_Participation, String Description, int Id_Participation) {
        this.participant = participant;
        this.challenge = challenge;
        this.IMG_Participation = IMG_Participation;
        this.Description = Description;
        this.Id_Participation = Id_Participation;
    }

    public AllUsers getParticipant() {
        return participant;
    }

    public void setParticipant(AllUsers participant) {
        this.participant = participant;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getIMG_Participation() {
        return IMG_Participation;
    }

    public void setIMG_Participation(String IMG_Participation) {
        this.IMG_Participation = IMG_Participation;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getId_Participation() {
        return Id_Participation;
    }

    public void setId_Participation(int Id_Participation) {
        this.Id_Participation = Id_Participation;
    }

    @Override
    public String toString() {
        return "Participation{" + "participant=" + participant + ", challenge=" + challenge + ", IMG_Participation=" + IMG_Participation + ", Description=" + Description + ", Id_Participation=" + Id_Participation + '}';
    }

}
