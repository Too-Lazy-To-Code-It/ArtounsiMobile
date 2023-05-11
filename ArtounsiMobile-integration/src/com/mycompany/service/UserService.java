/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;


import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entites.AllUsers;
import com.mycompany.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Adam
 */
public class UserService {
    public AllUsers CurrentUser;
    ConnectionRequest req;
    static UserService instance = null;

    //util
    boolean resultOK = false;
    List<AllUsers> users = new ArrayList<>();

    //Constructor
    private UserService() {
        req = new ConnectionRequest();
    }

    //Singleton
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    //ACTIONS
    //Add
    public int Verif(String $Email) {
        req = new ConnectionRequest();
        final int[] code = {0};
        JSONParser jp = new JSONParser();
        String addURL = Statics.Base_URL + "/json/vf";
        req.setUrl(addURL);
        req.setPost(false);
        req.addArgument("Email", $Email + "");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String jsonText = new String(req.getResponseData());
                try {
                    Map<String, Object> userJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
                    code[0] = ((Double) userJSON.get("code")).intValue();
                    System.out.println("CODE SERVICE : "+ code[0]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);


        return code[0];
    }

    public boolean addUser(AllUsers u) {

        req = new ConnectionRequest();
        //1
        String addURL = Statics.Base_URL + "/json/new";

        //2
        req.setUrl(addURL);
        //3
        req.setPost(false);
        //4
        req.addArgument("Name", u.getName());
        req.addArgument("LastName", u.getLast_Name() + "");
        req.addArgument("Nickname", u.getNickname() + "");
        req.addArgument("Email", u.getEmail() + "");
        req.addArgument("Birthday", u.getBirthday());
        req.addArgument("password", u.getPassword() + "");
        req.addArgument("Nationality", u.getNationality());
        req.addArgument("type", u.getType() + "");
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
    public List<AllUsers> fetchUsers() {

        req = new ConnectionRequest();

        //1
        String fetchURL = Statics.Base_URL + "/json/users";

        //2
        req.setUrl(fetchURL);

        //3
        req.setPost(false);

        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseusers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }

    //Parse
    public List<AllUsers> parseusers(String jsonText) {

        //var

        //DO
        //1
        JSONParser jp = new JSONParser();

        try {

            //2
            Map<String, Object> usersListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJSON.get("root");

            //4
            for (Map<String, Object> item : list) {

                AllUsers u = new AllUsers();
                u.setID_User((Integer) item.get("id_user"));
                u.setName((String) item.get("name"));
                u.setEmail((String) item.get("Email"));
                u.setLast_Name((String) item.get("LastName"));
                u.setNationality((String) item.get("Nationality"));
                u.setNickname((String) item.get("Nickname"));
                u.setPassword((String) item.get("Password"));
                u.setType((String) item.get("Type"));
                u.setBirthday((String) item.get("Birthday"));


                users.add(u);
            }

        } catch (IOException ignored) {
        }


        //End
        return users;
    }

    public boolean deleteuser(AllUsers u) {

        String url = Statics.Base_URL + "/json/deleteuser/" + u.getID_User();

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

    public boolean Edituser(AllUsers u) {
        req = new ConnectionRequest();

        //1
        String addURL = Statics.Base_URL + "/json/" + u.getID_User() + "/edit";

        //2
        req.setUrl(addURL);

        //3
        req.setPost(false);

        req.addArgument("name", u.getName());
        req.addArgument("Last_Name", u.getLast_Name() + "");
        req.addArgument("Nickname", u.getNickname() + "");
        req.addArgument("Email", u.getEmail() + "");
        req.addArgument("Birthday", u.getBirthday());
        req.addArgument("Password", u.getPassword() + "");
        req.addArgument("Nationality", u.getNationality());
        req.addArgument("type", u.getType() + "");
        req.addArgument("number", u.getNum() + "");

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

    public AllUsers parseuser(String jsonText) {
        JSONParser jp = new JSONParser();
        AllUsers u = new AllUsers();
        try {
            Map<String, Object> userJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            u.setID_User(((Double) userJSON.get("id_user")).intValue());
            u.setName((String) userJSON.get("name"));
            u.setEmail((String) userJSON.get("Email"));
            u.setLast_Name((String) userJSON.get("Last_Name"));
            u.setNationality((String) userJSON.get("nationality"));
            u.setNickname((String) userJSON.get("nickname"));
            u.setPassword((String) userJSON.get("password"));
            u.setType((String) userJSON.get("type"));
            u.setBirthday((String) userJSON.get("Birthday"));

            if((String) userJSON.get("number")==null)
                u.setNum("");
            else
                u.setNum((String) userJSON.get("number"));
        } catch (IOException ignored) {
        }
        return u;
    }

    public int login(AllUsers u) {
        final int[] code = {0};
        JSONParser jp = new JSONParser();
        req = new ConnectionRequest();
        String url = Statics.Base_URL + "/json/Login";
        req.setUrl(url);
        req.addArgument("Email", u.getEmail());
        req.addArgument("password", u.getPassword());
        final AllUsers[] user = {new AllUsers()};

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                user[0] = parseuser(new String(req.getResponseData()));
                String jsonText = new String(req.getResponseData());
                Map<String, Object> userJSON = null;
                try {
                    userJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(userJSON.get("code")==null || userJSON.get("code") =="")
                    code[0]=0;
                else
               code[0] = ((Double) userJSON.get("code")).intValue();
               
                   
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(user[0]);
        CurrentUser = user[0];
       
        return code[0];
    }


}
