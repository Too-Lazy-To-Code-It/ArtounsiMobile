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
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author amine
 */
public class ServiceCategory {
    
    public static ServiceCategory instance= null;
    
    
    public static boolean resultOk=true;
    
    //initillisation connection request
    private ConnectionRequest req;
    
    
    
    public static ServiceCategory getInstance(){
        if (instance ==null)
            instance = new ServiceCategory();
        return instance ;
        
    }
     
    
    public ServiceCategory(){
        req = new ConnectionRequest();
        
    }
    
    //ADDs
    public void addCategory(Category category){
        String url = Statics.Base_URL+"/category/newcategoryjson?name_category="+category.getName_category();
        
        req.setUrl(url);
        req.addResponseListener((e)->{
            
            String str = new String (req.getResponseData());//Response JSON THAT WE SAW IN THE NAVIGATOR 
            System.out.println("data=="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);//EXECUTION OF THE REQUEST OR NOTHING GO THROUGH 
    }
    
    //DISPLAY  
    public ArrayList<Category>displayCategory(){
        ArrayList<Category> result = new ArrayList<Category>();
        
            String url = Statics.Base_URL+"/showCat";
            req.setUrl(url);
            
            req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object>mapCategory = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> ListOfMaps = (List<Map<String,Object>>) mapCategory.get("root");
                    
                    
                    for(Map<String,Object>obj : ListOfMaps){
                        Category cat = new Category();
                        
                        //WE ALWAYS TAKE THE ID IN CODENAME ONE FLOAT 
                        float id_category = Float.parseFloat(obj.get("id_category").toString());
                        String name_category = obj.get("name_category").toString();
                        
                        cat.setId_category((int)id_category);
                        cat.setName_category(name_category);
                        
                        //INSERT DATA INTO ARRAYLIST RESULT 
                        result.add(cat);
                        
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
            
            NetworkManager.getInstance().addToQueueAndWait(req);//EXECUTION OF THE REQUEST OR NOTHING GO THROUGH 
            
            return result;
    }
    
    //DELETE CATEGORY 
    public boolean deleteCategory(int id_category) {
        String url = Statics.Base_URL+"/category/"+id_category+"/deletecategoryjson?";
        
        
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
    
    //Update 
    public boolean updateCategory(Category category){
        String url  = Statics.Base_URL+"/category/"+category.getId_category()+"/editcategoryjson?name_category="+category.getName_category();
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
