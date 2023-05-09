/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.entites.grosmot;
import com.mycompany.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
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
public class grosmotservice {

    //var
    ConnectionRequest req;
    static grosmotservice instance = null;

    //util
    boolean resultOK = false;
    List<grosmot> tasks = new ArrayList<>();

    //Constructor
    public grosmotservice() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static grosmotservice getInstance() {
        if (instance == null) {
            instance = new grosmotservice();
        }

        return instance;
    }

      //Parse
    public List<grosmot> parseTasks(String jsonText) {

        tasks = new ArrayList<>();
        JSONParser jp = new JSONParser();
        
        try {

            Map<String, Object> tasksListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJSON.get("root");
            for (Map<String, Object> item : list) {
                
               grosmot t = new grosmot();
               t.setIdMot(((Double)  item.get("idMot")).intValue());
                
                t.setMot((String)item.get("mot"));
                tasks.add(t);
            }
        
        } catch (IOException ex) {
        }
        
        
        
        //End
        return tasks;
    }

   

      public List<grosmot> fetchmot() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.Base_URL + "/grosmot";
        
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

 public Boolean grosMots(String words) {     
        List<grosmot> listBadWords = this.fetchmot();
      List<String> badWordNames = new ArrayList<>();

for (grosmot gm : listBadWords) {
    badWordNames.add(gm.getMot());
}
        
        String badWord = "";
        Boolean existe = false;
        String allbadwords = "";
        for (String str :  badWordNames) {
            if (words.toLowerCase().contains(str)) {
                badWord += "" + str;
                if (str.length() >= 1) {
                    StringBuilder badWordHiden = new StringBuilder();
                    badWordHiden.append(str.charAt(0));
                    for (int i = 0; i < str.length() - 2; ++i) {
                        badWordHiden.append("*");
                    }
                    badWordHiden.append(str.charAt(str.length() - 1));
                    str = badWordHiden.toString();
                    if (!str.isEmpty()) {
                        existe = true;
                        allbadwords += badWordHiden + "  ";
                    }
                }
            }
        }
        
        return existe;
    }

}
