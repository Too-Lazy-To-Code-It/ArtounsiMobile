/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

/**
 *
 * @author achref
 */
public class Video {
    private int id_video;
    private String title;
    private String date_p;
    private String description;
    private String pathimage;
    private String pathvideo;
    private int id_tutoriel;

    public Video() {
    }

    public Video(String title, String description, String pathimage, String pathvideo) {
        this.title = title;
        this.description = description;
        this.pathimage = pathimage;
        this.pathvideo = pathvideo;
    }
    
    public Video(int id_video, String title, String description, String pathimage, String pathvideo) {
        this.id_video = id_video;
        this.title = title;
        this.description = description;
        this.pathimage = pathimage;
        this.pathvideo = pathvideo;
    }

    public int getId_video() {
        return id_video;
    }

    public void setId_video(int id_vide) {
        this.id_video = id_vide;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_p() {
        return date_p;
    }

    public void setDate_p(String date_p) {
        this.date_p = date_p;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathimage() {
        return pathimage;
    }

    public void setPathimage(String pathimage) {
        this.pathimage = pathimage;
    }

    public String getPathvideo() {
        return pathvideo;
    }

    public void setPathvideo(String pathvideo) {
        this.pathvideo = pathvideo;
    }

    public int getId_tutoriel() {
        return id_tutoriel;
    }

    public void setId_tutoriel(int id_tutoriel) {
        this.id_tutoriel = id_tutoriel;
    }
    
    
}
