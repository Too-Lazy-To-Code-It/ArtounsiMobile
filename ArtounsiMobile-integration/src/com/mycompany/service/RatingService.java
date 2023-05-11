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
import com.mycompany.entites.Rating;
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
public class RatingService {
    
    ConnectionRequest req;
    static RatingService instance = null;

    //util
    boolean resultOK = false;
    List<Rating> ratings = new ArrayList<>();
    List<Video> videos = new ArrayList<>();

     //Constructor
    private RatingService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static RatingService getInstance() {
        if (instance == null) {
            instance = new RatingService();
        }

        return instance;
    }

    //ACTIONS
    //Add         
    /*public boolean deleteTutoriel(Tutoriel t) {

        //1
        String addURL = Statics.BASE_URL + "deletetutoriel/" + t.getId_tutoriel();

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
        String checkURL = Statics.BASE_URL + "checkfavoriTutoriel/" + t.getId_tutoriel();

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
    */
    public boolean rateTutoriel(Tutoriel t,int rating) {

        //1
        String addURL = Statics.BASE_URL + "/rateTutoriel/"+rating+"/"+t.getId_tutoriel()+"/"+UserService.getInstance().CurrentUser.getID_User();

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
    
    /*public boolean updatetutoriel(Tutoriel t) {

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
    }*/
        
    public List<Rating> fetchAVGRateTutoriel(Tutoriel t) {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/fetchAVGRateTutoriel"+"/"+t.getId_tutoriel();
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ratings = parseRatings(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ratings;
    }
    
    public List<Rating> fetchMyRateTutoriel(Tutoriel t) {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/fetchRateTutoriel"+"/"+t.getId_tutoriel()+"/"+UserService.getInstance().CurrentUser.getID_User();
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ratings = parseMyRating(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ratings;
    }

    //Parse
    public List<Rating> parseRatings(String jsonText) {

        //var
        ratings = new ArrayList<>();
        
        //DO
        //1
        JSONParser jp = new JSONParser();
        
        try {
            
            //2
            Map<String, Object> ratingsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) ratingsListJSON.get("root");
        
            //4
            for (Map<String, Object> item : list) {
                
                Rating r = new Rating();
                r.setAvg(((String) item.get("avg")));
                
                ratings.add(r);
            }
        
        } catch (IOException ex) {
            
        }
        
        
        
        //End
        return ratings;
    }
    
public List<Rating> parseMyRating(String jsonText) {

        //var
        ratings = new ArrayList<>();
        
        //DO
        //1
        JSONParser jp = new JSONParser();
        
        try {
            
            //2
            Map<String, Object> ratingsListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) ratingsListJSON.get("root");
        
            //4
            for (Map<String, Object> item : list) {
                
                Rating r = new Rating();
                r.setRating(((Double) item.get("rating")).intValue());
                
                ratings.add(r);
            }
        
        } catch (IOException ex) {
            
        }
        
        
        
        //End
        return ratings;
    }
    
}


// r.setAvg(((String) item.get("avg")));
