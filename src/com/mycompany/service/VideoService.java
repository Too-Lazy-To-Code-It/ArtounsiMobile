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
/**
 *
 * @author achref
 */
public class VideoService {
    ConnectionRequest req;
    static VideoService instance = null;

    private VideoService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static VideoService getInstance() {
        if (instance == null) {
            instance = new VideoService();
        }

        return instance;
    }
    //util
    boolean resultOK = false;
    
        public boolean addVideo(Video v,int id_tutoriel) {

        //1
        String addURL = Statics.BASE_URL + "/addVideo/"+id_tutoriel;

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
        req.addArgument("title", v.getTitle());
        req.addArgument("description", v.getDescription());
        req.addArgument("pathimage", v.getPathimage());
        req.addArgument("pathvideo", v.getPathvideo()+ "");
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
    
    public boolean modifyVideo(Video v, Tutoriel t) {

        //1
        String addURL = Statics.BASE_URL + "/modifyVideo/"+v.getId_video()+"/"+t.getId_tutoriel();

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        //4
        req.addArgument("title", v.getTitle());
        req.addArgument("description", v.getDescription());
        req.addArgument("pathimage", v.getPathimage());
        req.addArgument("pathvideo", v.getPathvideo()+ "");
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
    
        public boolean deleteVideo(Video v) {

        //1
        String addURL = Statics.BASE_URL + "/deleteVideo/" + v.getId_video();

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
    
}
