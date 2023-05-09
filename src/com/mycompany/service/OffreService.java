/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.entites.Offre;

import com.mycompany.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;

import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;

import com.mycompany.entites.AllUsers;
import com.mycompany.service.UserService;
import java.util.Map;

import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author khaledguedria
 */
public class OffreService {

    //var
    ConnectionRequest req;
    static OffreService instance = null;

    //util
    boolean resultOK = false;
    List<Offre> tasks = new ArrayList<>();
    String verif="";

    //Constructor
    private OffreService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static OffreService getInstance() {
        if (instance == null) {
            instance = new OffreService();
        }

        return instance;
    }

    //ACTIONS
    //Add
    public boolean addoffre(Offre t) {

       int id=1;
        String addURL = Statics.Base_URL + "/newjson/"+UserService.getInstance().CurrentUser.getID_User();;

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
        req.addArgument("titreoffre", t.getTitreoffre());
        req.addArgument("descriptionoffre", t.getDescriptionoffre() + "");
        req.addArgument("descriptionoffre", t.getDescriptionoffre() + "");
        req.addArgument("idcategorie", t.getIdcategorie() + "");
        req.addArgument("typeoffre", t.getTypeoffre() + "");
        req.addArgument("localisationoffre", t.getLocalisationoffre() + "");

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

    //FETCH
    public List<Offre> fetchoffres() {
        
        req = new ConnectionRequest();
     
        String fetchURL = Statics.Base_URL + "/showoffr";
        
     
        req.setUrl(fetchURL);
       
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
      public List<Offre> fetchmyoffres() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.Base_URL + "/json/mesoffres/"+UserService.getInstance().CurrentUser.getID_User();
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

    //Parse
    public List<Offre> parseTasks(String jsonText) {

        tasks = new ArrayList<>();
        JSONParser jp = new JSONParser();
        
        try {

            Map<String, Object> tasksListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJSON.get("root");
            for (Map<String, Object> item : list) {
                
                Offre t = new Offre();
               t.setIdoffre(((Double)  item.get("idoffre")).intValue());
               // t.setId_user(((Double) item.get("id_user")).intValue());
                t.setTitreoffre((String) item.get("titreoffre"));
                t.setDescriptionoffre((String) item.get("descriptionoffre"));
                t.setTypeoffre((String) item.get("typeoffre"));
               // t.setIdcategorie(((Double) item.get("categorie")).intValue());
                t.setCategorieoffre((String)item.get("categorieoffre"));
                t.setLocalisationoffre((String)item.get("localisationoffre"));
                t.setNickname((String)item.get("nickname"));
                tasks.add(t);
            }
        
        } catch (IOException ex) {
        }
        
        
        
        //End
        return tasks;
    }


    
     public boolean deleteoffre(Offre o) {
            
        String url = Statics.Base_URL +"/deleteoffre/"+o.getIdoffre();
        
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
     
public boolean Editoffre(Offre t) {
int id=1;
        //1
        String addURL = Statics.Base_URL + "/updatejson/"+t.getIdoffre()+"/"+UserService.getInstance().CurrentUser.getID_User();;

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        req.addArgument("titreoffre", t.getTitreoffre());
        req.addArgument("descriptionoffre", t.getDescriptionoffre() + "");
        req.addArgument("descriptionoffre", t.getDescriptionoffre() + "");
        req.addArgument("idcategorie", t.getIdcategorie() + "");
        req.addArgument("typeoffre", t.getTypeoffre() + "");
        req.addArgument("localisationoffre", t.getLocalisationoffre() + "");
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


  public String postuler(int idoffre, int iduserconnected) {
        
        req = new ConnectionRequest();
     
        String fetchURL = Statics.Base_URL + "/"+idoffre+"/mail/"+UserService.getInstance().CurrentUser.getID_User()+"/";
        
     
        req.setUrl(fetchURL);
       
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               verif =new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return verif;
    }

}
