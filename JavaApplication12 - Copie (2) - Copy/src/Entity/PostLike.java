/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author amine
 */
public class PostLike {
    private int id_post,id_user, id_PostLike;
    private int postId;
    private int numLikes;
    private String post_title;

    public PostLike() {
    }

    public PostLike(int id_post, int id_user, int id_PostLike, int postId, int numLikes) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.id_PostLike = id_PostLike;
        this.postId = postId;
        this.numLikes = numLikes;
    }

    public PostLike(int id_user, int id_PostLike, int postId, int numLikes) {
        this.id_user = id_user;
        this.id_PostLike = id_PostLike;
        this.postId = postId;
        this.numLikes = numLikes;
    }

    public PostLike(int id_post, int id_user, int id_PostLike, int postId, int numLikes, String post_title) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.id_PostLike = id_PostLike;
        this.postId = postId;
        this.numLikes = numLikes;
        this.post_title = post_title;
    }

    public PostLike(int numLikes, String post_title) {
        this.numLikes = numLikes;
        this.post_title = post_title;
    }

    public PostLike(String post_title, int num_likes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
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

    public int getId_PostLike() {
        return id_PostLike;
    }

    public void setId_PostLike(int id_PostLike) {
        this.id_PostLike = id_PostLike;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public PostLike(int postId, int numLikes) {
        this.postId = postId;
        this.numLikes = numLikes;
    }

    @Override
    public String toString() {
        return "PostLike{" + "id_post=" + id_post + ", id_user=" + id_user + ", id_PostLike=" + id_PostLike + ", postId=" + postId + ", numLikes=" + numLikes + '}';
    }

   }
