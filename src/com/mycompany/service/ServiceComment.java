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
import com.mycompany.entites.Comment;
import com.mycompany.entites.Post;
import static com.mycompany.service.ServiceCategory.resultOk;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author amine
 */
public class ServiceComment {
    public static ServiceComment instance= null;
    
    public static boolean resultOk=true;
    
    
     //initillisation connection request
    private ConnectionRequest req;
    
    public static ServiceComment getInstance(){
        if (instance ==null)
            instance = new ServiceComment();
        return instance ;
    }
    
    
    private ServiceComment(){
        req = new ConnectionRequest();
    }
    
    //ADD COMMENT METHOD :
        public void addComment(int id_post,Comment comment){
        //&post_type=blog
        //String url = Statics.Base_URL+"/comment/newjson/"+comment.getId_post()+"?&id_user=1&comment="+comment.getComment();
        String url = Statics.Base_URL+"/comment/newjson/"+id_post+"?&id_user=1&comment="+comment.getComment();
        
        req.setUrl(url);
        req.addResponseListener((e)->{
            
            String str = new String (req.getResponseData());//Response JSON THAT WE SAW IN THE NAVIGATOR 
            System.out.println("data=="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);//EXECUTION OF THE REQUEST OR NOTHING GO THROUGH 
    }
        
    //DISPLAY COMMENT
//        public void displayComment(Comment comment){
//        String url = Statics.Base_URL+"/comment/JSONCOMMENTSHOW/26?";
//        req.setUrl(url);
//        req.addResponseListener((e)->{
//            
//            String str = new String (req.getResponseData());//Response JSON THAT WE SAW IN THE NAVIGATOR 
//            System.out.println("data=="+str);
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);//EXECUTION OF THE REQUEST OR NOTHING GO THROUGH 
//        }
        
        
        
        //For Test
      //  int postId = 1;
    public ArrayList<Comment> displayComment(int postId) {
    ArrayList<Comment> result = new ArrayList<Comment>();
//    String url = Statics.Base_URL + "/comment/JSONCOMMENTSHOW/26?/" + postId;
String url = Statics.Base_URL + "/comment/JSONCOMMENTSHOW/" + postId;
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String, Object> mapComment = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapComment.get("root");
                for (Map<String, Object> obj : listOfMaps) {
                    Comment comment = new Comment();
                    float id_comment = Float.parseFloat(obj.get("id_comment").toString());
                    comment.setId_comment((int) id_comment);
                    comment.setComment(obj.get("comment").toString());
                    // To get the user id:
                    // float id_user = Float.parseFloat(((Map<String, Object>)obj.get("user")).get("id_user").toString());
                    // comment.setId_user((int) id_user);
                    result.add(comment);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return result;
}
    
    
    
    
    //DELETE CATEGORY 
    public boolean deleteComment(int id_comment) {
        String url = Statics.Base_URL+"/comment/"+id_comment+"/deletecommentjson?";
        
        
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
    
    
    //UPDATE COMMENT 
       public boolean updateComment(Comment comment){
        String url  = Statics.Base_URL+"/comment/"+comment.getId_comment()+"/editcommentjson?comment="+comment.getComment();
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
