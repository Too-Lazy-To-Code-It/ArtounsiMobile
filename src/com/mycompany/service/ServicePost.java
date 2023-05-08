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
import com.mycompany.utils.Statics;
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
    
    
    public ArrayList<Post>displayPost(){
        ArrayList<Post> result = new ArrayList<Post>();
        
         String url = Statics.Base_URL+"/blogjon";
         req.setUrl(url);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object>mapPost = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> ListOfMaps = (List<Map<String,Object>>) mapPost.get("root");
                    
                    
                    for(Map<String,Object>obj : ListOfMaps){
                        Post post = new Post();
                        
                        //WE ALWAYS TAKE THE ID IN CODENAME ONE FLOAT 
                        float id_post = Float.parseFloat(obj.get("id_post").toString());
                        String title_p = obj.get("title_p").toString();
                        post.setId_post((int)id_post);
                        post.setTitle_p(title_p);
                        
                        //INSERT DATA INTO ARRAYLIST RESULT 
                        result.add(post);
                        
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
         NetworkManager.getInstance().addToQueueAndWait(req);//EXECUTION OF THE REQUEST OR NOTHING GO THROUGH 
            
            return result;
    }

    
    
}
