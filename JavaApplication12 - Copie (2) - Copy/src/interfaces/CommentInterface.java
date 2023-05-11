/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import models.Comment;
import models.Post;
import java.util.List;

/**
 *
 * @author amine
 */
public interface CommentInterface {
     //add
    public void addComment(Comment co);
    public void deleteComment(int commentId);
    //public void modifyComment(Comment co);
    public void modifyComment(Comment c, String newComment) ;
    //public Comment fetchCommentByPostId(int id_post);
    //public List<Comment> fetchCommentsByPostId(int id_post);
    public Post fetchPost(int id_post);
    public List<Post> fetchPosts();
    public Comment fetchCommentByPostId(String title);
    
    
    // i use this : 
public List<Comment> fetchCommentByPostId(int postId);
    
}
