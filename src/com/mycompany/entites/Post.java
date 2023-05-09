/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entites;

import java.util.Date;

/**
 *
 * @author amine
 */
public class Post {
    private int id_post;
    private int id_user;
    private String description_p;
    private String media;
    private String title_p;
    private String imagePath;
    private Date date_p;
    private String post_type;
    private int likes;
    private String path;
    private Category category_p;
    private int likeCount;

    public Post() {
    }

    public Post(int id_post, int id_user, String description_p, String media, String title_p, String imagePath, Date date_p, String post_type, int likes, String path, Category category_p, int likeCount) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.description_p = description_p;
        this.media = media;
        this.title_p = title_p;
        this.imagePath = imagePath;
        this.date_p = date_p;
        this.post_type = post_type;
        this.likes = likes;
        this.path = path;
        this.category_p = category_p;
        this.likeCount = likeCount;
    }

    public Post(int id_user, String description_p, String media, String title_p, String imagePath, Date date_p, String post_type, int likes, String path, Category category_p, int likeCount) {
        this.id_user = id_user;
        this.description_p = description_p;
        this.media = media;
        this.title_p = title_p;
        this.imagePath = imagePath;
        this.date_p = date_p;
        this.post_type = post_type;
        this.likes = likes;
        this.path = path;
        this.category_p = category_p;
        this.likeCount = likeCount;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDescription_p() {
        return description_p;
    }

    public void setDescription_p(String description_p) {
        this.description_p = description_p;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getTitle_p() {
        return title_p;
    }

    public void setTitle_p(String title_p) {
        this.title_p = title_p;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getDate_p() {
        return date_p;
    }

    public void setDate_p(Date date_p) {
        this.date_p = date_p;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Category getCategory_p() {
        return category_p;
    }

    public void setCategory_p(Category category_p) {
        this.category_p = category_p;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

  
    
    
}