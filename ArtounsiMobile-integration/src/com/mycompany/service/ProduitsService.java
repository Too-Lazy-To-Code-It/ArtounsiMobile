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
import com.mycompany.entites.Produits;
import com.mycompany.utils.Statics;
import java.lang.Integer;

import java.io.IOException;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;


/**
 *
 * @author aouad
 */
public class ProduitsService {
     //var
    ConnectionRequest req;
    static ProduitsService instance = null;

    //util
    boolean resultOK = false;
    List<Produits> produits = new ArrayList<>();
    
    //Constructor
    private ProduitsService() {
        req = new ConnectionRequest();
    }
    
      //Singleton
    public static ProduitsService getInstance() {
        if (instance == null) {
            instance = new ProduitsService();
        }

        return instance;
    }
    
    
    //ACTIONS
     //Add
     public boolean addProduit(Produits p) {
         
       int id_user=1;


        //1
     
     String addURL = Statics.BASE_URL + "/ajouterProduitJSON/new/"+UserService.getInstance().CurrentUser.getID_User()+"?" +
                "nom=" + p.getNom() +
                "&description=" + p.getDescription() +
                "&category=" + p.getCategory().getName_category() +
                "&image=" + p.getImage() +
                "&prix=" + Double.toString(p.getPrix()) +
                "&id_user=1";


        //2
        req.setUrl(addURL);
        System.out.println(addURL);
      
        req.addResponseListener((e) -> {
            
             String str = new String(req.getResponseData());
             System.out.println("data == " + str);
        });
     
       
    

         // Send the request and wait for its completion
    NetworkManager.getInstance().addToQueueAndWait(req);
        return false;

     }   
     
     
     
  //FETCH
    public ArrayList<Produits> fetchProduits() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/TousLesproduits/mobile";
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produits = parseProduits(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return (ArrayList<Produits>) produits;
    }

    
    
    
      //Afficher un produit 
   public Produits fetchUnProduit(int idproduit) {
    req = new ConnectionRequest();
    String fetchURL = Statics.BASE_URL + "/ShowProduitMobile/" + idproduit;
    req.setUrl(fetchURL);
    req.setPost(false);

    Produits produit = new Produits(); // initialiser produit ici

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            Produits p = parseUnProduit(new String(req.getResponseData()));
            produit.setIdproduit(p.getIdproduit());
            produit.setNom(p.getNom());
            produit.setDescription(p.getDescription());
            produit.setImage(p.getImage());
            produit.setPrix(p.getPrix());
            produit.setCategory(p.getCategory());
           
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return produit;
}

    
  //Modifier produit   
    public boolean modifierProduit(Produits p) {
     //int id_user=1;
        //1
       // String addURL = Statics.Base_URL + "/newjson/"+UserService.getInstance().CurrentUser.getID_User();;
       String addURL = Statics.BASE_URL + "/modifierProduitJSON/"+p.getIdproduit()+"/"+UserService.getInstance().CurrentUser.getID_User();

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
     
        req.addArgument("title", p.getNom());
        req.addArgument("description", p.getDescription());
        req.addArgument("image", p.getImage());
        req.addArgument("category", p.getCategory().getName_category());
        req.addArgument("prix", Double.toString(p.getPrix()));
      
        System.out.println(req);

        //5
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
     
     
    //Parse de tous les produits 
    public List<Produits> parseProduits(String jsonText)  {

        //var
        produits = new ArrayList<>();
        
        //DO
        //1
        JSONParser jp = new JSONParser();
        
        try {
            
            //2
            Map<String, Object> produitsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) produitsListJSON.get("root");
        
            //4
            for (Map<String, Object> item : list) {
                
                Produits p = new Produits();
               
               p.setIdproduit(((Double) item.get("idproduit")).intValue());

                p.setNom((String) item.get("nom"));
                p.setDescription((String) item.get("description"));
                p.setImage((String) item.get("image"));
                p.setPrix((double) item.get("prix"));
                p.setCategory((Category) item.get("category"));
               
    
                produits.add(p);
            }
        
        } catch (IOException ex) {
        }
        
        
        
        //End
        return produits;
    }

  
    
    //Parse d'un seule produit
public Produits parseUnProduit(String jsonText) {
    Produits p = new Produits();

    JSONParser jp = new JSONParser();

    try {
        Map<String, Object> produitJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        p.setIdproduit((int)  produitJSON.get("idproduit"));
  
        p.setNom((String) produitJSON.get("nom"));
        p.setDescription((String) produitJSON.get("description"));
        p.setImage((String) produitJSON.get("image"));
        p.setPrix((double) produitJSON.get("prix"));
        p.setCategory((Category) produitJSON.get("category"));
      
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }

    return p;
}
    
    
    
 //Supprimer un produit 
  
    
    public boolean supprimerProduit(Produits p) {
            
        String url = Statics.Base_URL + "/supprimerProduitJSON/" + p.getIdproduit();
        
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
     
     
     
     
  
    
}
