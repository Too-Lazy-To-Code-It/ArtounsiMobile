/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entites;
import java.util.Date;

/**
 *
 * @author Adam
 */
public class Comment {
     private int id_comment;
     private Post id_post;
     private int id_user;
     private Date date_comment;
     private String comment;

    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }
    

    public Comment(int id_comment, Post id_post, int id_user, Date date_comment, String comment) {
        this.id_comment = id_comment;
        this.id_post = id_post;
        this.id_user = id_user;
        this.date_comment = date_comment;
        this.comment = comment;
    }

    public Comment(Post id_post, int id_user, Date date_comment, String comment) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.date_comment = date_comment;
        this.comment = comment;
    }

    public int getId_comment() {
        return id_comment;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public Post getId_post() {
        return id_post;
    }

    public void setId_post(Post id_post) {
        this.id_post = id_post;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDate_comment() {
        return date_comment;
    }

    public void setDate_comment(Date date_comment) {
        this.date_comment = date_comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

   
     
}
