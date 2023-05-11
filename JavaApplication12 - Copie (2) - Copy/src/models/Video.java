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
public class Video {
    private int ID_Video;
    private Tutoriel Tutoriel;
    private String Title, Date_p, Descrption, pathVideo, pathImage;

    public Video() {
    }

    public Video(int ID_Video, Tutoriel Tutoriel, String Title, String Date_p, String Descrption, String pathVideo, String pathImage) {
        this.ID_Video = ID_Video;
        this.Tutoriel = Tutoriel;
        this.Title = Title;
        this.Date_p = Date_p;
        this.Descrption = Descrption;
        this.pathVideo = pathVideo;
        this.pathImage = pathImage;
    }

    public int getID_Video() {
        return ID_Video;
    }

    public void setID_Video(int ID_Video) {
        this.ID_Video = ID_Video;
    }

    public Tutoriel getTutoriel() {
        return Tutoriel;
    }

    public void setTutoriel(Tutoriel Tutoriel) {
        this.Tutoriel = Tutoriel;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDate_p() {
        return Date_p;
    }

    public void setDate_p(String Date_p) {
        this.Date_p = Date_p;
    }

    public String getDescrption() {
        return Descrption;
    }

    public void setDescrption(String Descrption) {
        this.Descrption = Descrption;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    @Override
    public String toString() {
        return "Video{" + "ID_Video=" + ID_Video + ", Tutoriel=" + Tutoriel + ", Title=" + Title + ", Date_p=" + Date_p + ", Descrption=" + Descrption + ", pathVideo=" + pathVideo + ", pathImage=" + pathImage + '}';
    }
    
}
