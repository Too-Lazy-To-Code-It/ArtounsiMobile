/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.mycompany.service;

import com.mycompany.entites.Tutoriel;
import com.mycompany.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entites.Video;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author achref
 */
public class TutorielService {
    
    ConnectionRequest req;
    RatingService rs = RatingService.getInstance();
    static TutorielService instance = null;

    //util
    boolean resultOK = false;
    List<Tutoriel> tutoriels = new ArrayList<>();
    List<Video> videos = new ArrayList<>();

     //Constructor
    private TutorielService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static TutorielService getInstance() {
        if (instance == null) {
            instance = new TutorielService();
        }

        return instance;
    }

    //ACTIONS
    //Add         
    public boolean deleteTutoriel(Tutoriel t) {

        //1
        String addURL = Statics.BASE_URL + "/deletetutoriel/" + t.getId_tutoriel();

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
    
        public String checkfavoriTutoriel(Tutoriel t) {

        //1
        String checkURL = Statics.BASE_URL + "/checkfavoriTutoriel/" + t.getId_tutoriel();

        //2
        req.setUrl(checkURL);

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
            System.out.println(new String(req.getResponseData()));

        return new String(req.getResponseData());
    }
    
    public boolean addTutoriel(Tutoriel t) {

        //1
        String addURL = Statics.BASE_URL + "/addTutoriel";

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
        req.addArgument("title", t.getTitle());
        req.addArgument("description", t.getDescription());
        req.addArgument("niveau", t.getNiveau()+ "");
        req.addArgument("pathimg", t.getPathimg());
        req.addArgument("id_artist", t.getId_artist()+ "");
        req.addArgument("id_categorie", t.getId_categorie()+ "");    
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
    
    public boolean updatetutoriel(Tutoriel t) {

        //1
        String addURL = Statics.BASE_URL + "/updatetutoriel/"+t.getId_tutoriel();

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
        req.addArgument("title", t.getTitle());
        req.addArgument("description", t.getDescription());
        req.addArgument("niveau", t.getNiveau()+ "");
        req.addArgument("pathimg", t.getPathimg());
        req.addArgument("id_artist", t.getId_artist()+ "");
        req.addArgument("id_categorie", t.getId_categorie());    
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
        
    public List<Tutoriel> fetchFavorisTutoriels() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/showfavorisTutoriels"+"/"+UserService.getInstance().CurrentUser.getID_User();
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tutoriels = parseTutoriels(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tutoriels;
    }
    
    public List<Tutoriel> fetchBestTutoriels() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/showbestTutoriels";
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tutoriels = parseTutoriels(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tutoriels;
    }
    
    //FETCH
    public List<Tutoriel> fetchTutoriels() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/showlisttutoriels";
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tutoriels = parseTutoriels(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tutoriels;
    }

    //Parse
    public List<Tutoriel> parseTutoriels(String jsonText) {

        //var
        tutoriels = new ArrayList<>();
        
        //DO
        //1
        JSONParser jp = new JSONParser();
        
        try {
            
            //2
            Map<String, Object> tutorielsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) tutorielsListJSON.get("root");
        
            //4
            for (Map<String, Object> item : list) {
                
                Tutoriel t = new Tutoriel();
                t.setId_tutoriel(((Double) item.get("id_tutoriel")).intValue());
                t.setTitle((String) item.get("title"));
                t.setDescription((String) item.get("description"));
                t.setNiveau(((Double) item.get("niveau")).intValue());
                t.setPathimg((String) item.get("pathimg"));
                t.setAvg_rating(rs.fetchAVGRateTutoriel(t).get(0).getAvg());
                
                tutoriels.add(t);
            }
        
        } catch (IOException ex) {
            
        }
        
        
        
        //End
        return tutoriels;
    }

    public List<Video> fetchVideos(int id) {
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/showtutoriel/"+id;
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                videos = parseVideos(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return videos;
    }
    
        public List<Video> parseVideos(String jsonText) {

        //var
        videos = new ArrayList<>();
        
        //DO
        //1
        JSONParser jp = new JSONParser();
        
        try {
            
            //2
            Map<String, Object> tutorielsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) tutorielsListJSON.get("root");
        
            //4
            for (Map<String, Object> item : list) {
                
                Video v = new Video();
                v.setId_video(((Double)item.get("id_video")).intValue());
                v.setTitle((String) item.get("title"));
                v.setDescription((String) item.get("description"));
                v.setPathimage((String) item.get("pathimage"));
                v.setPathvideo((String) item.get("pathvideo"));
                v.setDate_p((String) item.get("date_p"));

                /*t.setId_tutoriel(((Double) item.get("id_tutoriel")).intValue());
                t.setTitle((String) item.get("title"));
                t.setDescription((String) item.get("description"));
                t.setNiveau(((Double) item.get("niveau")).intValue());
                t.setPathimg((String) item.get("pathimg"));*/
                videos.add(v);
            }
        
        } catch (IOException ex) {
            
        }
        
        
        
        //End
        return videos;
    }
        
}
