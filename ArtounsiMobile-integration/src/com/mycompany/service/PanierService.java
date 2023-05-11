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
import com.mycompany.entites.Lignepanier;
import com.mycompany.entites.Panier;
import com.mycompany.entites.Produits;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aouad
 */
public class PanierService {

      //var
    ConnectionRequest req;
    static PanierService instance = null;
 ArrayList<Lignepanier> result = new ArrayList<>();
    //util
    boolean resultOK = false;
    List<Lignepanier> lp = new ArrayList<>();
    
    
     ArrayList<Produits> result2 = new ArrayList<>();
    //Constructor
    private PanierService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static PanierService getInstance() {
        if (instance == null) {
            instance = new PanierService();
        }

        return instance;
    }
 public ArrayList<Produits> produits;
//    //FETCH
//    public List<Lignepanier> fetchPanier() {
//        
//
//        
//        //1
//        String fetchURL = Statics.BASE_URL + "/showPanierJson";
//        
//        //2
//        req.setUrl(fetchURL);
//        
//        //3
//        req.setPost(false);
//        
//        //4
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                lp  = parseLignepanier(new String(req.getResponseData()));
//                req.removeResponseListener(this);
//            }
//        });
//        
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return lp;
//    }
//    
    
    
    
      public ArrayList<Produits> parsePanierElementsProducts(String jsonText){
        try {
            result2 =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
           
            //4
            for (Map<String, Object> item : list) {
                
                Produits p = new Produits();
               
               p.setIdproduit(((Double) item.get("idproduit")).intValue());

                p.setNom((String) item.get("nom"));
                p.setPrix((double) item.get("prix"));
                p.setImage((String) item.get("image"));
    
                 result2.add(p);
                  System.out.println("tesssssstttt "+result2);
            }
           
        } catch (IOException ex) {
            
        }
        return result2;
    }
  
    
       public ArrayList<Produits> getPanierElementsProducts(){
       
       
        req=new ConnectionRequest();
        String url = Statics.BASE_URL+"/showPanierJson2/"+UserService.getInstance().CurrentUser.getID_User();;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result2 = parsePanierElementsProducts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result2;
          
    }
 
       
      public ArrayList<Lignepanier> parsePanierElements(String jsonText){
        try {
            result =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Lignepanier t = new Lignepanier();
                float idlignepanier = Float.parseFloat(obj.get("idlignepanier").toString());
          
                t.setIdlignepanier((int)idlignepanier);
             
                result.add(t);
                System.out.println("tesssssstttt2222 "+ result);
            }
        } catch (IOException ex) {
            
        }
        return result;
    }
    
    
     public ArrayList<Lignepanier> getPanierElements(){
      
       
          req=new ConnectionRequest();
        String url = Statics.BASE_URL+"/showPanierJson/"+UserService.getInstance().CurrentUser.getID_User();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = parsePanierElements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
    
    
    
    
    
      
 //Supprimer un produit du panier
  
    
    public boolean supprimerProduitduPanier(Lignepanier lp ) {
            
        String url = Statics.Base_URL + "/deleteligneJson/" + lp.getIdlignepanier();
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                      resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
          return resultOK;
    } 
     
      
// Vider un panier 
  //Vider la panier
    public boolean ViderPanier(int id ) {
        String url = Statics.BASE_URL +"/ViderPanierJSON?id="+(int)id; 
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    resultOK = req.getResponseCode() == 200;
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOK;
    }    
    
    // Ajouter prod au panier 
       public boolean AjouterProdAuPanier(Produits p) {
        String url = Statics.BASE_URL +"/addJson/"+p.getIdproduit()+"/"+UserService.getInstance().CurrentUser.getID_User();;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;    
}
    
    
    
    
    
    
    
    
}
