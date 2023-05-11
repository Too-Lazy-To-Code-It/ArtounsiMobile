/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import java.sql.Date;

/**
 *
 * @author amine
 */
public class Comment {
     int id_comment, id_user;
    Post post_c;
    Date datec ;
    String Comment;

    public Comment() {
    }

    public Comment(int id_comment, int id_user, Post post_c, Date datec, String Comment) {
        this.id_comment = id_comment;
        this.id_user = id_user;
        this.post_c = post_c;
        this.datec = datec;
        this.Comment = Comment;
    }

    public Comment(int id_user, Post post_c, Date datec, String Comment) {
        this.id_user = id_user;
        this.post_c = post_c;
        this.datec = datec;
        this.Comment = Comment;
    }

    public int getId_comment() {
        return id_comment;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Post getPost_c() {
        return post_c;
    }

    public void setPost_c(Post post_c) {
        this.post_c = post_c;
    }

    public Date getDatec() {
        return datec;
    }

    public void setDatec(Date datec) {
        this.datec = datec;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    

   

   
}
