/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entites.Category;
import com.mycompany.entites.Post;
import com.mycompany.entites.PostLike;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author amine
 */
public class ServicePost {
    public static ServicePost instance= null;
    
    public static boolean resultOk=true;
    
    //initillisation connection request
    private ConnectionRequest req;
    
    public static ServicePost getInstance(){
        if (instance ==null)
            instance = new ServicePost();
        return instance ;
    }
    
    
    public ServicePost(){
        req = new ConnectionRequest();  
    }
    //ADD
//    public void addPost(Post post){
//        //&post_type=blog
//        String url = Statics.Base_URL+"/post/newpostjson?id_user=1&id_category="+post.getCategory_p().getId_category()+"&description_p="
//                +post.getDescription_p()+"&media="+post.getMedia()+"&title_p="+post.getTitle_p()+"&post_type="+post.getPost_type();
//        
//        req.setUrl(url);
//        req.addResponseListener((e)->{
//            
//            String str = new String (req.getResponseData());//Response JSON THAT WE SAW IN THE NAVIGATOR 
//            System.out.println("data=="+str);
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);//EXECUTION OF THE REQUEST OR NOTHING GO THROUGH 
//    }
    
    public void addPost(Post post){
    //&post_type=blog
    String url = Statics.Base_URL+"/post/newpostjson?id_user=1&id_category="+post.getCategory_p().getId_category()+"&description_p="
            +post.getDescription_p()+"&media="+post.getMedia()+"&title_p="+post.getTitle_p()+"&post_type="+post.getPost_type();
    
    req.setUrl(url);
    req.addResponseListener((e)->{
        
        String str = new String (req.getResponseData());//Response JSON THAT WE SAW IN THE NAVIGATOR 
        System.out.println("data=="+str);
    });
    NetworkManager.getInstance().addToQueueAndWait(req);//EXECUTION OF THE REQUEST OR NOTHING GO THROUGH 
}

    
    
    //Display postlike
        //Display postlike
//    public ArrayList<PostLike> displayPostLike(int id_like){
//    ArrayList<PostLike> result = new ArrayList<PostLike>();
//    PostLike postlike = new PostLike();
//    String url = Statics.Base_URL +"/post/like/jsonpostlike/" + id_like;
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                JSONParser jsonp = new JSONParser();
//                try {
//                    Map<String, Object> mapPostLike = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
//                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPostLike.get("root");
//                    for (Map<String, Object> obj : listOfMaps) {
//                        float id_like = Float.parseFloat(obj.get("id_like").toString());
//                        postlike.setId_like((int) id_like);
//                        result.add(postlike);
//                    }
//                    
//                } catch (IOException ex) {
//                   ex.printStackTrace();
//                }
//            }
//        });
//         NetworkManager.getInstance().addToQueueAndWait(req);
//         return result;
//    }
//    public ArrayList<PostLike> displayPostLike(int id_like) {
//    ArrayList<PostLike> result = new ArrayList<PostLike>();
//    String url = Statics.Base_URL + "/post/like/jsonpostlike/" + id_like;
//    ConnectionRequest req = new ConnectionRequest();
//    req.setUrl(url);
//    req.setHttpMethod("GET");
//    req.addResponseListener(new ActionListener<NetworkEvent>() {
//        @Override
//        public void actionPerformed(NetworkEvent evt) {
//            JSONParser jsonp = new JSONParser();
//            try {
//                Map<String, Object> mapPostLike = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
//                int id_post = Math.round(Float.parseFloat(mapPostLike.get("id_post").toString()));
//
//                int id_user = Integer.parseInt(mapPostLike.get("id_user").toString());
//                PostLike postLike = new PostLike(id_like, id_post, id_user);
//                result.add(postLike);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    });
//    NetworkManager.getInstance().addToQueueAndWait(req);
//    return result;
//}


    //THIS WORKS FINE
    public ArrayList<Post> displayPost() {
    ArrayList<Post> result = new ArrayList<Post>();
    String url = Statics.Base_URL + "/blogjon";
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String, Object> mapPost = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPost.get("root");
                for (Map<String, Object> obj : listOfMaps) {
                    
                    Post post = new Post();
                    String title_p = obj.get("title_p").toString();
                    String post_type= obj.get("post_type").toString();
                    post.setTitle_p(title_p);
                    //System.out.println("moinfdgmoi");
                    if (post_type.equals("portfolio")) { // Check if the title_p is "blog"
                       // Post post = new Post();
                        float id_post = Float.parseFloat(obj.get("id_post").toString());
                        
                        post.setId_post((int) id_post);
                        post.setTitle_p(title_p);
                        post.setDescription_p(obj.get("description_p").toString());
                        post.setMedia(obj.get("media").toString());
                        post.setPost_type(post_type);
                        //System.out.println("injiqmgdg"+post_type);
                        result.add(post);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return result;
}
//   public ArrayList<Post> displayPost() {
//    ArrayList<Post> result = new ArrayList<Post>();
//    String url = Statics.Base_URL + "/blogjon";
//    req.setUrl(url);
//    req.addResponseListener(new ActionListener<NetworkEvent>() {
//        @Override
//        public void actionPerformed(NetworkEvent evt) {
//            JSONParser jsonp = new JSONParser();
//            try {
//                Map<String, Object> mapPost = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
//                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPost.get("root");
//                for (Map<String, Object> obj : listOfMaps) {
//                    
//                    Post post = new Post();
//                    String title_p = obj.get("title_p").toString();
//                    String post_type= obj.get("post_type").toString();
//                    post.setTitle_p(title_p);
//                    //System.out.println("moinfdgmoi");
//                    if (post_type.equals("portfolio")) { // Check if the title_p is "blog"
//                        int id_post = Integer.parseInt(obj.get("id_post").toString());
//
//                        post.setId_post((int) id_post);
//                        post.setTitle_p(title_p);
//                        post.setDescription_p(obj.get("description_p").toString());
//                        post.setMedia(obj.get("media").toString());
//                        post.setPost_type(post_type);
//                        
//                        // Retrieve like count for this post
//                        String likeUrl = Statics.Base_URL + "/jsonpostlike/" + id_post;
//                        ConnectionRequest likeReq = new ConnectionRequest();
//                        likeReq.setUrl(likeUrl);
//                        NetworkManager.getInstance().addToQueueAndWait(likeReq);
//                        JSONParser likeJsonp = new JSONParser();
//                        Map<String, Object> likeMap = likeJsonp.parseJSON(new CharArrayReader(new String(likeReq.getResponseData()).toCharArray()));
//                        int likeCount = (int) likeMap.get("count");
//                        
//                        post.setLikeCount(likeCount);
//                        
//                        result.add(post);
//                    }
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    });
//    NetworkManager.getInstance().addToQueueAndWait(req);
//    return result;
//}
//

    
    
    //Display BLOG
    public ArrayList<Post> displayBlog() {
    ArrayList<Post> result = new ArrayList<Post>();
    String url = Statics.Base_URL + "/blogjon";
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String, Object> mapPost = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPost.get("root");
                for (Map<String, Object> obj : listOfMaps) {
                    
                    Post post = new Post();
                    String title_p = obj.get("title_p").toString();
                    String post_type= obj.get("post_type").toString();
                    post.setTitle_p(title_p);
                    //System.out.println("moinfdgmoi");
                    if (post_type.equals("blog")) { // Check if the title_p is "blog"
                       // Post post = new Post();
                        float id_post = Float.parseFloat(obj.get("id_post").toString());
                        
                        post.setId_post((int) id_post);
                        post.setTitle_p(title_p);
                        post.setDescription_p(obj.get("description_p").toString());
                        post.setMedia(obj.get("media").toString());
                        post.setPost_type(post_type);
                        //System.out.println("injiqmgdg"+post_type);
                        result.add(post);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return result;
}

    
    
    //DELETE Post 
    public boolean deletePost(int id_post) {
        String url = Statics.Base_URL+"/post/"+id_post+"/DeletePostJson?";
        //http://localhost:8000/post/81/DeletePostJson?
        
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
       //Update  Post
    public boolean updatePost(Post post){
        String url  = Statics.Base_URL+"/post/"+post.getId_post()+"/editpostjson?title_p="+post.getTitle_p()+"&description_p="+post.getDescription_p();
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ; //CODE RESPONSE HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    
    
}
