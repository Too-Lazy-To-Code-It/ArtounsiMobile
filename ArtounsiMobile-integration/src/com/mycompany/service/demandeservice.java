/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.entites.demande;
import com.mycompany.service.demandeservice;
import com.mycompany.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;

import java.util.Map;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author khaledguedria
 */
public class demandeservice {

    //var
    ConnectionRequest req;
    static demandeservice instance = null;

    //util
    boolean resultOK = false;
    List<demande> tasks = new ArrayList<>();

    //Constructor
    private demandeservice() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static demandeservice getInstance() {
        if (instance == null) {
            instance = new demandeservice();
        }

        return instance;
    }

    //ACTIONS
    //Add
    public boolean adddemande(demande t) {
int id =1;
        //1
        String addURL = Statics.Base_URL + "/newdemande/"+UserService.getInstance().CurrentUser.getID_User();

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
        req.addArgument("titreDemande", t.getTitreDemande());
        req.addArgument("descriptionDemande", t.getDescriptionDemande() + "");
       
        req.addArgument("idcategorie", t.getIdcategorie() + "");
        req.addArgument("pdf", t.getPdf() + "");
      

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
    public List<demande> fetchdemandes() {
        
        req = new ConnectionRequest();
     
        String fetchURL = Statics.Base_URL + "/json/alldemandes";
        
     
        req.setUrl(fetchURL);
       
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasksdemande(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
      public List<demande> fetchmydemandes() {
        
        req = new ConnectionRequest();
        int id=1;
        //1
        String fetchURL = Statics.Base_URL + "/json/mesdemandes/"+UserService.getInstance().CurrentUser.getID_User();
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasksdemande(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

    //Parse
    public List<demande> parseTasksdemande(String jsonText) {

        tasks = new ArrayList<>();
        JSONParser jp = new JSONParser();
        
        try {

            Map<String, Object> tasksListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJSON.get("root");
            for (Map<String, Object> item : list) {
                
                demande t = new demande();
              t.setIdDemande(((Double)  item.get("idDemande")).intValue());
                //t.setId_user(((Double) item.get("id_user")).intValue());
                t.setTitreDemande((String) item.get("titreDemande"));
                t.setDescriptionDemande((String) item.get("descriptionDemande"));
                
                t.setCategoriedemande((String)item.get("categoriedemande"));
             // t.setPdf((String)item.get("pdf"));
               t.setNickname((String)item.get("nickname"));
                tasks.add(t);
            }
        
        } catch (IOException ex) {
        }
        
        
        
        //End
        return tasks;
    }


    
     public boolean deletedemande(demande o) {
            
        String url = Statics.Base_URL +"/deletedemande/"+o.getIdDemande();
        
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
     
public boolean Editdemande(demande t) {
int id=1;
        //1
        String addURL = Statics.Base_URL + "/updatedemande/"+t.getIdDemande()+"/"+UserService.getInstance().CurrentUser.getID_User();

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        req.addArgument("titreDemande", t.getTitreDemande());
        req.addArgument("descriptionDemande", t.getDescriptionDemande() + "");
    
        req.addArgument("idcategorie", t.getIdcategorie() + "");
        req.addArgument(" pdf", t.getPdf() + "");
      

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
}
