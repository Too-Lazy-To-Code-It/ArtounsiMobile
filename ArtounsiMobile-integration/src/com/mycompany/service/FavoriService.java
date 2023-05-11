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
import com.mycompany.entites.Favori;
import com.mycompany.entites.Tutoriel;
import com.mycompany.entites.Video;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author achref
 */
public class FavoriService {
    ConnectionRequest req;
    static FavoriService instance = null;

    //util
    boolean resultOK = false;
    List<Favori> favoris = new ArrayList<>();
    List<Video> videos = new ArrayList<>();

     //Constructor
    private FavoriService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static FavoriService getInstance() {
        if (instance == null) {
            instance = new FavoriService();
        }

        return instance;
    }

    //ACTIONS
    //Add         
    public boolean deleteFavori(Tutoriel t) {

        //1
        String addURL = Statics.BASE_URL + "/removefavoriTutoriel/" + t.getId_tutoriel()+"/"+UserService.getInstance().CurrentUser.getID_User();

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
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
    
    public boolean addFavori(Tutoriel t) {

        //1
        String addURL = Statics.BASE_URL + "/addfavoriTutoriel/"+t.getId_tutoriel()+"/"+UserService.getInstance().CurrentUser.getID_User() ;

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

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
    public List<Favori> checkFavoris(Tutoriel t) {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/checkfavoriTutoriel/"+t.getId_tutoriel()+"/"+UserService.getInstance().CurrentUser.getID_User();
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                favoris = parseFavoris(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return favoris;
    }
    
    public List<Favori> countFavoris(Tutoriel t) {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/fetchCountFavorisTutoriel/"+t.getId_tutoriel();
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                favoris = parseFavoris(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return favoris;
    }

    //Parse
    public List<Favori> parseFavoris(String jsonText) {

        //var
        favoris = new ArrayList<>();
        
        //DO
        //1
        JSONParser jp = new JSONParser();
        
        try {
            
            //2
            Map<String, Object> favorisListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) favorisListJSON.get("root");
        
            //4
            for (Map<String, Object> item : list) {
                
                Favori f = new Favori();
                f.setId_favoris(((Double) item.get("id_favoris")).intValue());
                favoris.add(f);
            }
        
        } catch (IOException ex) {
            
        }
        
        
        
        //End
        return favoris;
    }
}
