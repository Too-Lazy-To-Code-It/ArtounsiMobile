/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import models.Category;
import models.Post;
import models.PostLike;
import java.util.List;

/**
 *
 * @author amine
 */
public interface PostInterface {
     /*************New code****************/
    public void addPost(Post p);
    public void deletePost(int postId);
    public void modifyPost(Post p, String newTitle, String newDescription);
    public void addLike(Post p);
    public void deleteLike(int id_post, int id_user);
    public List<Post> fetchPortfolioPostDetails();
    public List<Post> fetchPostBlogPostDetails();
    public List<Post> fetchPostByCategory(int categoryId);
    public List<Post> fetchPortfolioPostDetailsOfThePortfolioCreater();
    /*************End new code****************/
    public Category getCategoryByName(String categoryName);

  
   
public List<PostLike> isLikedByUser(int id_post, int id_user);
public List<PostLike> Number_Of_Likes_For_A_Post(int id_post, int id_user);
public List<String> getPostTitles();
public int getPostIdByTitle(String title);
public String getPostTitleById(int postId);
public List<Category> fetchCategories();
public List<Post> fetchPosts();
public List<PostLike> Number_Of_Likes_For_A_Post_Post(int id_post);

//
//public List<Post> getLikesByUser(int userId);
//
Post fetchPostByTitle(String title);
 public Post getPostById(int postId);
 //using it 
 
 public PostLike getPostWithHighestLikes();

 public int getNumLikes(int postId);
 
 public List<Integer> getLikeIdsForPost(int postId);
}

