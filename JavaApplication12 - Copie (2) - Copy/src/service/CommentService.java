/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import models.Category;
import models.Comment;
import models.Post;
import Interfaces.CommentInterface;
import Interfaces.PostInterface;
import util.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Logged;

/**
 *
 * @author amine
 */
public class CommentService implements CommentInterface{
    
    //var
    Connection cnx = MyConnection.getInstance().getCnx();
    
@Override
public void addComment(Comment co) {
    try {
        Date datec = new Date();
        java.sql.Timestamp sqldate = new java.sql.Timestamp(datec.getTime());
        
        // Check if the post exists
        String checkPostQuery = "SELECT COUNT(*) FROM post WHERE id_post = ?";
        PreparedStatement checkPostStmt = cnx.prepareStatement(checkPostQuery);
        checkPostStmt.setInt(1, co.getPost_c().getId_post());
        ResultSet checkPostResult = checkPostStmt.executeQuery();
        checkPostResult.next();
        int postCount = checkPostResult.getInt(1);
        if (postCount == 0) {
            System.out.println("Error adding comment: Post does not exist");
            return;
        }
        
        String insertCommentQuery = "INSERT INTO comment(Id_post, Date_Comment, Comment, ID_User) VALUES (?, ?, ?, ?)";
        PreparedStatement insertCommentStmt = cnx.prepareStatement(insertCommentQuery);
        insertCommentStmt.setInt(1, co.getPost_c().getId_post());
        insertCommentStmt.setTimestamp(2, sqldate);
        insertCommentStmt.setString(3, co.getComment());
        insertCommentStmt.setInt(4, Logged.get_instance().getUser().getID_User());
        int result = insertCommentStmt.executeUpdate();
        if (result > 0) {
            System.out.println("Comment added successfully");
        } else {
            System.out.println("Error adding comment: No rows affected");
        }
    } catch (SQLException ex) {
        System.out.println("Error adding comment: " + ex.getMessage());
    }
}

    @Override
    public void deleteComment(int commentId) {
            try {
            String req = "DELETE FROM comment WHERE Id_comment = ?";
            PreparedStatement com = cnx.prepareStatement(req);
            com.setInt(1, commentId);
            int result = com.executeUpdate();
            if (result > 0) {
                System.out.println("Comment deleted");
            } else {
                System.out.println("No comment found with Id_comment " + commentId);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    @Override
//    public void modifyComment(Comment co) {
//            try {
//            String req = "UPDATE comment SET Comment = ? WHERE Id_comment = ?";
//            PreparedStatement com = cnx.prepareStatement(req);
//            com.setString(1, co.getComment());
//            com.setInt(2, co.getId_comment());
//            int result = com.executeUpdate();
//            if (result > 0) {
//                System.out.println("Comment modified");
//            } else {
//                System.out.println("No comment found with Id_comment " + co.getId_comment());
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }

//    @Override
//public void modifyComment(Comment c, String newComment) {
//    try {
//        // check if the comment exists in the database
//        String checkComment = "SELECT * FROM comment WHERE Id_comment=?";
//        PreparedStatement checkCommentStmt = cnx.prepareStatement(checkComment);
//        checkCommentStmt.setInt(1, c.getId_comment());
//        ResultSet rs = checkCommentStmt.executeQuery();
//        if (!rs.next()) {
//            System.out.println("Comment does not exist in the database");
//            return;
//        }
//
//        // update the comment in the database
//        String req = "UPDATE comment SET Comment=? WHERE Id_comment=?";
//        PreparedStatement st = cnx.prepareStatement(req);
//        st.setString(1, newComment);
//        st.setInt(2, c.getId_comment());
//        int result = st.executeUpdate();
//        if (result > 0) {
//            System.out.println("Comment modified successfully!");
//        } else {
//            System.out.println("Comment not modified");
//        }
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//    }
//}
//    @Override
//    public void modifyComment(Comment co, String newCommentText) {
//    try {
//        String req = "UPDATE comment SET Comment = ? WHERE Id_comment = ?";
//        PreparedStatement com = cnx.prepareStatement(req);
//        com.setString(1, newCommentText);
//        com.setInt(2, co.getId_comment());
//        int result = com.executeUpdate();
//        if (result > 0) {
//            System.out.println("Comment modified");
//            co.setComment(newCommentText); // update the comment text in the Comment object
//        } else {
//            System.out.println("No comment found with Id_comment " + co.getId_comment());
//        }
//    } catch (SQLException ex) {
//        System.out.println(ex.getMessage());
//    }
//}
    @Override
    public void modifyComment(Comment co, String newCommentText) {
        try {
            // check if the comment exists in the database
            String checkComment = "SELECT * FROM comment WHERE Comment=?";
            PreparedStatement checkCommentStmt = cnx.prepareStatement(checkComment);
            checkCommentStmt.setString(1, co.getComment());
            ResultSet rs = checkCommentStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Comment does not exist in the database");
                return;
            }

            // update the comment in the database
            String req = "UPDATE comment SET Comment=? WHERE Comment=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, newCommentText);
            st.setString(2, co.getComment());
            int result = st.executeUpdate();
            if (result > 0) {
                System.out.println("Comment modified successfully!");
                co.setComment(newCommentText); // update the comment text in the Comment object
            } else {
                System.out.println("Comment not modified");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    @Override
       public List<Post> fetchPosts() {
            List<Post> posts = new ArrayList<>();
            try {

               String req = "SELECT * FROM post";
               Statement st = cnx.createStatement();
               ResultSet rs = st.executeQuery(req);
               while (rs.next()) {                
                   Post p = new Post();
                   p.setDescription_p(rs.getString(1));
                   p.setMedia(rs.getString(2));
                   p.setTitle(rs.getString(3));
                   p.setPost_typ(rs.getString(4));
                   posts.add(p);
               }

           } catch (SQLException ex) {
               ex.printStackTrace();
           }

           return posts;
       }
 

    
        @Override
        public Post fetchPost(int id_post) {
            Post po = null;
            try {
                String req = "SELECT * FROM post WHERE id_post = ?";
                PreparedStatement st = cnx.prepareStatement(req);
                st.setInt(1, id_post);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    po = new Post();
                    po.setId_post(rs.getInt("id_post"));
                    po.setTitle(rs.getString("Title_p"));
                    po.setMedia(rs.getString("Media"));
                    po.setDescription_p(rs.getString("Description_p"));
                    po.setPost_typ(rs.getString("post_type"));
                    //po.setDate_p(rs.getDate("Date_p"));
                    Category cat = new Category();
                    cat.setId_Category(rs.getInt("Id_Category"));
                    po.setCategory_p(cat);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return po;
        }

    @Override
    public Comment fetchCommentByPostId(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        public List<Comment> fetchCommentByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();
        try {
            String query = "SELECT * FROM comment WHERE Id_post = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, postId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Comment comment = new Comment();
                //comment.setId_user(result.getInt("ID_User "));
                Post post = new Post();
                post.setId_post(result.getInt("Id_post"));
                comment.setPost_c(post);
                //comment.setDate_comment(result.getTimestamp("Date_Comment"));
                comment.setComment(result.getString("Comment"));
                comments.add(comment);
            }
            if (comments.isEmpty()) {
                System.out.println("No comments found for post with ID " + postId);
            } else {
                System.out.println("Comments for post with ID " + postId + ":");
                for (Comment comment : comments) {
                    System.out.println("- " + comment.getComment());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching comments: " + ex.getMessage());
        }
        return comments;
    } 
        
        public List<Integer> getUserIdsByPostId(int postId) {
    List<Integer> userIds = new ArrayList<>();
    try {
        String query = "SELECT DISTINCT ID_User FROM comment WHERE Id_post = ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setInt(1, postId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            userIds.add(result.getInt("ID_User"));
        }
        if (userIds.isEmpty()) {
            System.out.println("No users found for post with ID " + postId);
        } else {
            System.out.println("Users for post with ID " + postId + ":");
            for (int userId : userIds) {
                System.out.println("- " + userId);
            }
        }
    } catch (SQLException ex) {
        System.out.println("Error fetching users: " + ex.getMessage());
    }
    return userIds;
}
        
public int getIdCommentByComment(String comment) {
    int idComment = -1; // initialize to an invalid value
    
    try {
        String query = "SELECT ID_comment FROM comment WHERE Comment = ?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setString(1, comment);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            idComment = result.getInt("ID_comment");
        }
    } catch (SQLException ex) {
        System.out.println("Error getting ID_comment: " + ex.getMessage());
    }
    
    return idComment;
}
        
public int getIdUserByCommentId(int commentId) {
    int idUser = -1; // initialize to an invalid value
    
    try {
        String query = "SELECT ID_User FROM comment WHERE ID_comment = ?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setInt(1, commentId);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            idUser = result.getInt("ID_User");
        }
    } catch (SQLException ex) {
        System.out.println("Error getting ID_User: " + ex.getMessage());
    }
    
    return idUser;
}



        }
    

    


