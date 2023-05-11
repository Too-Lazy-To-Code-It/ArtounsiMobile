/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import models.Category;
import models.Post;
import models.PostLike;
import Interfaces.PostInterface;
import util.MyConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Logged;

/**
 *
 * @author amine
 */
public class PostService implements PostInterface{

     //var
    Connection cnx = MyConnection.getInstance().getCnx();
    
/******************************************************************************/


    //New 
    @Override
    public void addPost(Post p) {
        try {
            // Get current time as SQL timestamp
            java.sql.Timestamp sqldate = new java.sql.Timestamp(System.currentTimeMillis());

            // Check if the post_type is valid
            if (!"Portfolio".equals(p.getPost_typ()) && !"Blog".equals(p.getPost_typ())) {
                System.out.println("Invalid post_type value");
                return;
            }
            // Add the post to the database
            String req = "INSERT INTO post (Description_p, Media, Title_p, Date_p, Id_Category, post_type, ID_User) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = cnx.prepareStatement(req);
            //st.setInt(1, p.getId_user());  
            st.setString(1, p.getDescription_p());
            st.setString(2, p.getMedia());
            st.setString(3, p.getTitle());
            st.setTimestamp(4, sqldate);
            st.setInt(5, p.getCategory_p().getId_Category());
            st.setString(6, p.getPost_typ());
            st.setInt(7, Logged.get_instance().getUser().getID_User()); 
            //st.setInt(7, p.getId_user(Logged.get_instance().getUser().getID_User())); 
            int result = st.executeUpdate();
            if (result > 0) {
                System.out.println("Post added successfully!");
            } else {
                System.out.println("Post not added");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    

    
    public void modifyPost(Post p, String newTitle, String newDescription) {
    try {
        // check if the post exists in the database
        String checkPost = "SELECT * FROM post WHERE Title_p=?";
        PreparedStatement checkPostStmt = cnx.prepareStatement(checkPost);
        checkPostStmt.setString(1, p.getTitle());
        ResultSet rs = checkPostStmt.executeQuery();
        if (!rs.next()) {
            System.out.println("Post does not exist in the database");
            return;
        }

        // update the post in the database
        String req = "UPDATE post SET Title_p=?, Description_p=? WHERE Title_p=?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, newTitle);
        st.setString(2, newDescription);
        st.setString(3, p.getTitle());
        int result = st.executeUpdate();
        if (result > 0) {
            System.out.println("Post modified successfully!");
        } else {
            System.out.println("Post not modified");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    
          
          @Override
            public void deletePost(int postId) {
                        try {
                // delete the comments associated with the post from the database
                String req1 = "DELETE FROM comment WHERE Id_post=?";
                PreparedStatement st1 = cnx.prepareStatement(req1);
                st1.setInt(1, postId);
                st1.executeUpdate();

                // delete the post likes associated with the post from the database
                String req2 = "DELETE FROM post_like WHERE Id_post=?";
                PreparedStatement st2 = cnx.prepareStatement(req2);
                st2.setInt(1, postId);
                st2.executeUpdate();

                // delete the post from the database
                String req3 = "DELETE FROM post WHERE Id_post=?";
                PreparedStatement st3 = cnx.prepareStatement(req3);
                st3.setInt(1, postId);
                int result = st3.executeUpdate();
                if (result > 0) {
                    System.out.println("Post deleted successfully!");
                } else {
                    System.out.println("Post not found");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
                
}
          
            
            @Override
        public void addLike(Post p) {
            try {
                // Check if the user has already liked the post
                String checkReq = "SELECT * FROM `post_like` WHERE `id_post` = ? AND `ID_User` = ?";
                PreparedStatement checkSt = cnx.prepareStatement(checkReq);
                checkSt.setInt(1, p.getId_post());
                checkSt.setInt(2, p.getId_user());
                ResultSet rs = checkSt.executeQuery();
                if (rs.next()) {
                    System.out.println("User has already liked the post");
                    return; // User has already liked the post, so we return without inserting a new like
                }

                // User has not liked the post yet, so we can insert a new like into the post_like table
                String addReq = "INSERT INTO `post_like`(`id_post`, `ID_User`) VALUES (?,?)";
                PreparedStatement addSt = cnx.prepareStatement(addReq);
                addSt.setInt(1, p.getId_post());
                addSt.setInt(2, p.getId_user());
                addSt.executeUpdate();
                System.out.println("Like added successfully!");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    @Override
    public void deleteLike(int id_post, int id_user) {
           try {
          String req = "DELETE FROM post_like WHERE id_post = ? and ID_User = ?";
          PreparedStatement st = cnx.prepareStatement(req);
          st.setInt(1, id_post);
          st.setInt(2, id_user);
          st.executeUpdate();
          System.out.println("Like Deleted Successfully!");
      } catch (SQLException ex) {
          ex.printStackTrace();
      }
    }

   public List<PostLike> isLikedByUser(int id_post, int id_user) {
        List<PostLike> posts = new ArrayList<>();
        String req = "SELECT * FROM post_like WHERE id_post = ? and id = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id_post);
            st.setInt(2, id_user);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int post_id = rs.getInt("id_post");
                    int user_id = rs.getInt("id");
                    PostLike p = new PostLike(post_id, user_id);
                    posts.add(p);
                }
            }
            System.out.println("this post is like by the user " );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
}
    
   @Override
    public List<PostLike> Number_Of_Likes_For_A_Post(int id_post, int id_user) {
             List<PostLike> postLikes = new ArrayList<>();
            String req = "SELECT * FROM post_like WHERE id_post = ? AND ID_User = ?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id_post);
                st.setInt(2, id_user);
                try (ResultSet resultSet = st.executeQuery()) {
                    while (resultSet.next()) {
                        //int id_PostLike = resultSet.getInt("id_PostLike");
                        int post_id = resultSet.getInt("id_post");
                        int user_id = resultSet.getInt("ID_User");
                        PostLike postLike = new PostLike(post_id, user_id);
                        postLikes.add(postLike);
                    }
                    int count = postLikes.size();
                    System.out.println("Number of Likes for this post is  " +count);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return postLikes;
    }
    
    // get all post likes
    public List<PostLike> getAllPostLikes() {
    List<PostLike> postLikes = new ArrayList<>();
    String req = "SELECT * FROM post_like";
    try (Statement st = cnx.createStatement();
         ResultSet resultSet = st.executeQuery(req)) {
        while (resultSet.next()) {
            int post_id = resultSet.getInt("id_post");
            int user_id = resultSet.getInt("ID_User");
            PostLike postLike = new PostLike(post_id, user_id);
            postLikes.add(postLike);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return postLikes;
}
    
    //New im using this one yep 
    
        @Override
    public List<PostLike> Number_Of_Likes_For_A_Post_Post(int id_post) {
        List<PostLike> postLikes = new ArrayList<>();
        String req = "SELECT * FROM post_like WHERE id_post = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id_post);
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    int post_id = resultSet.getInt("id_post");
                    int user_id = resultSet.getInt("ID_User");
                    PostLike postLike = new PostLike(post_id, user_id);
                    postLikes.add(postLike);
                }
                int count = postLikes.size();
                System.out.println("Number of Likes for this post is " + count);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return postLikes;
    }

    @Override
public PostLike getPostWithHighestLikes() {
    String req = "SELECT Id_post, COUNT(*) as num_likes FROM post_like GROUP BY id_post ORDER BY num_likes DESC LIMIT 1";
    try (Statement st = cnx.createStatement();
         ResultSet resultSet = st.executeQuery(req)) {
        if (resultSet.next()) {
            int post_id = resultSet.getInt("id_post");
            int num_likes = resultSet.getInt("num_likes");
            return new PostLike(post_id, num_likes);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}

@Override
public int getNumLikes(int postId) {
    int numLikes = 0;
    try {
        String req = "SELECT COUNT(*) as num_likes FROM post_like WHERE id_post = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, postId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            numLikes = rs.getInt("num_likes");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return numLikes;
}

public List<PostLike> getAllPostLikesorder() throws SQLException {
    List<PostLike> postLikes = new ArrayList<>();
    String req = "SELECT Id_post, COUNT(*) as num_likes FROM post_like GROUP BY id_post ORDER BY num_likes DESC";
    try (Statement st = cnx.createStatement();
         ResultSet resultSet = st.executeQuery(req)) {
        while (resultSet.next()) {
            int post_id = resultSet.getInt("id_post");
            int num_likes = resultSet.getInt("num_likes");
            postLikes.add(new PostLike(post_id, num_likes));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return postLikes;
}


    @Override
        public List<Post> fetchPostByCategory(int categoryId) {
            List<Post> posts = new ArrayList<>();
            try {
                // check if the category exists in the database
                String checkCategory = "SELECT * FROM category WHERE Id_Category=?";
                PreparedStatement checkCategoryStmt = cnx.prepareStatement(checkCategory);
                checkCategoryStmt.setInt(1, categoryId);
                ResultSet rs = checkCategoryStmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("Category does not exist in the database");
                    return posts;
                }

                // fetch posts for the specified category ID
                String req = "SELECT * FROM post WHERE Id_Category=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, categoryId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Post post = new Post();
                    post.setId_user(rs.getInt("id"));
                    //post.setId_post(rs.getInt("Id_post"));
                    post.setDescription_p(rs.getString("Description_p"));
                    post.setTitle(rs.getString("Title_p"));
                    //post.setDate_p(rs.getTimestamp("Date_p"));
                    post.setPost_typ(rs.getString("post_type"));
                    Category category = fetchCategoryById(rs.getInt("Id_Category"));
                    post.setCategory_p(category);
                    posts.add(post);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return posts;
        }
        private Category fetchCategoryById(int categoryId) {
            Category category = null;
            try {
                String req = "SELECT * FROM category WHERE Id_Category=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, categoryId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    category = new Category();
                    category.setId_Category(rs.getInt(1));
                    category.setName_category(rs.getString(2));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return category;
        }
        
        //new
        public List<String> getPostTitles() {
            List<String> titles = new ArrayList<>();
            try {
                String req = "SELECT Title_p FROM post";
                PreparedStatement st = cnx.prepareStatement(req);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    titles.add(rs.getString("Title_p"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return titles;
        }

    @Override
    public int getPostIdByTitle(String title) {
     int id = 0;
     try {
         String req = "SELECT Id_post FROM post WHERE Title_p=?";
         PreparedStatement st = cnx.prepareStatement(req);
         st.setString(1, title);
         ResultSet rs = st.executeQuery();
         if (rs.next()) {
             id = rs.getInt("Id_post");
         }
         rs.close();
         st.close();
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return id;
    }
    
    
     public String getPostTitleById(int postId) {
        String title = null;
        try {
            String req = "SELECT Title_p FROM post WHERE Id_post=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, postId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                title = rs.getString("Title_p");
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return title;
    }   
     
         @Override
    public List<Category> fetchCategories() {
         List<Category> categories = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM category";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                Category c = new Category();
                c.setId_Category(rs.getInt(1));
                c.setName_category(rs.getString(2));
                categories.add(c);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return categories;
    }

    //NEW FETCHPOTS
    @Override
    public List<Post> fetchPosts() {
        List<Post> posts = new ArrayList<>();
        try {

            String req = "SELECT * FROM post";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Post p = new Post();
                p.setId_post(rs.getInt("Id_post"));
                p.setDescription_p(rs.getString("Description_p"));
                p.setMedia(rs.getString("Media"));
                p.setTitle(rs.getString("Title_p"));
                p.setPost_typ(rs.getString("post_type"));

                // Get the number of likes for this post
                List<PostLike> likes = Number_Of_Likes_For_A_Post_Post(p.getId_post());
                int numberOfLikes = likes.size();
               // p.setNumberOfLikes(numberOfLikes);
                

                posts.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return posts;
    }

        @Override
    public List<Post> fetchPostBlogPostDetails() {
        List<Post> posts = new ArrayList<>();
        try {
            String req = "SELECT Id_post ,Description_p, Title_p, Media FROM post WHERE post_type=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, "Blog");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                p.setId_post(rs.getInt("Id_post"));
                p.setDescription_p(rs.getString("Description_p"));
                p.setMedia(rs.getString("Media"));
                p.setTitle(rs.getString("Title_p"));
                p.setPost_typ("Blog");
                // Get the number of likes for this post
                List<PostLike> likes = Number_Of_Likes_For_A_Post_Post(p.getId_post());
                int numberOfLikes = likes.size();
                posts.add(p);    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posts;
    }
    //Portfolio
           @Override
    public List<Post> fetchPortfolioPostDetails() {
        List<Post> posts = new ArrayList<>();
        try {
            String req = "SELECT Id_post ,Description_p, Title_p, Media FROM post WHERE post_type=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, "Portfolio");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                p.setId_post(rs.getInt("Id_post"));
                p.setDescription_p(rs.getString("Description_p"));
                p.setMedia(rs.getString("Media"));
                p.setTitle(rs.getString("Title_p"));
                p.setPost_typ("Portfolio");
                // Get the number of likes for this post
                List<PostLike> likes = Number_Of_Likes_For_A_Post_Post(p.getId_post());
                int numberOfLikes = likes.size();
                posts.add(p);    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posts;
    }
    
    @Override
public List<Post> fetchPortfolioPostDetailsOfThePortfolioCreater() {
    List<Post> posts = new ArrayList<>();
    try {
        String req = "SELECT Id_post ,Description_p, Title_p, Media FROM post WHERE post_type=? AND ID_User=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, "Portfolio");
        ps.setInt(2, Logged.get_instance().getUser().getID_User());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Post p = new Post();
            p.setId_post(rs.getInt("Id_post"));
            p.setDescription_p(rs.getString("Description_p"));
            p.setMedia(rs.getString("Media"));
            p.setTitle(rs.getString("Title_p"));
            p.setPost_typ("Portfolio");
            // Get the number of likes for this post
            List<PostLike> likes = Number_Of_Likes_For_A_Post_Post(p.getId_post());
            int numberOfLikes = likes.size();
            posts.add(p);    
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return posts;
}
    
        public Category getCategoryByName(String categoryName) {
        try {
            String sql = "SELECT * FROM category WHERE Name_Category=?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setId_Category(rs.getInt("Id_Category"));
                category.setName_category(rs.getString("Name_Category"));
                return category;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
        
        
        //maybe
        @Override
        public Post fetchPostByTitle(String title) {
            try {
                String req = "SELECT * FROM post WHERE Title_p = ?";
                PreparedStatement st = cnx.prepareStatement(req);
                st.setString(1, title);
                ResultSet res = st.executeQuery();

                // Check if the result set has any rows
                if (!res.next()) {
                    return null;
                }

                // Create a new Post object and populate it with the data from the result set
                Post post = new Post();
                post.setId_user(res.getInt("id"));
                post.setDescription_p(res.getString("Description_p"));
                post.setMedia(res.getString("Media"));
                post.setTitle(res.getString("Title_p"));
                java.sql.Timestamp sqldate = res.getTimestamp("Date_p");
                //post.setDate(new java.util.Date(sqldate.getTime()));
                //Category category = categoryService.fetchCategoryById(res.getInt("Id_Category"));
                //post.setCategory_p(category);
                post.setPost_typ(res.getString("post_type"));

                return post;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    @Override
    public Post getPostById(int postId) {
        Post post = null;
        String query = "SELECT * FROM post WHERE Id_post=?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, postId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                post = new Post();
                post.setId_post(result.getInt("Id_post"));
                post.setTitle(result.getString("Title_p"));
                post.setMedia(result.getString("Media"));
                post.setDescription_p(result.getString("Description_p"));
                //post.setDate_p(result.getTimestamp("Date_p"));
                post.setPost_typ(result.getString("post_type"));

                int categoryId = result.getInt("Id_Category");
                //Category category = getCategoryById(categoryId);
               // post.setCategory_p(category);
            } else {
                System.out.println("No post found with id: " + postId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return post;
    }
    
    
    
    public int getIdUserByTitle(String title) {
    int idUser = -1; // default value in case title is not found
    try {
        String req = "SELECT ID_User FROM post WHERE Title_p=?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, title);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            idUser = rs.getInt("ID_User");
        }
        rs.close();
        st.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return idUser;
}
@Override
public List<Integer> getLikeIdsForPost(int postId) {
    List<Integer> likeIds = new ArrayList<Integer>();
    try {
        String selectReq = "SELECT `id_like` FROM `post_like` WHERE `id_post` = ?";
        PreparedStatement selectSt = cnx.prepareStatement(selectReq);
        selectSt.setInt(1, postId);
        ResultSet rs = selectSt.executeQuery();
        while (rs.next()) {
            int likeId = rs.getInt("id_like");
            likeIds.add(likeId);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return likeIds;
}

public List<Integer> getUserIdsByPostId(int postId) {
    List<Integer> userIds = new ArrayList<>();
    try {
        String query = "SELECT `ID_User` FROM `post_like` WHERE `id_like` = ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setInt(1, postId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int userId = resultSet.getInt("ID_User");
            userIds.add(userId);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return userIds;
}
    
    }
