/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

/**
 *
 * @author amine
 */
public class PostLike {
    private int id_like ;
   // private int id_post;
    private Post id_post;
    private int id_user;
     private int likeCount;

    public PostLike() {
    }

    public PostLike(int id_like, Post id_post, int id_user, int likeCount) {
        this.id_like = id_like;
        this.id_post = id_post;
        this.id_user = id_user;
        this.likeCount = likeCount;
    }

    public PostLike(Post id_post, int id_user, int likeCount) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.likeCount = likeCount;
    }

    public int getId_like() {
        return id_like;
    }

    public void setId_like(int id_like) {
        this.id_like = id_like;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

   
}
