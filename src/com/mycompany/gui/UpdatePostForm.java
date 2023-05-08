/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Post;
import com.mycompany.service.ServiceCategory;
import com.mycompany.service.ServicePost;

/**
 *
 * @author amine
 */
public class UpdatePostForm extends BaseForm{
    
    Form current;
    
    public UpdatePostForm(Resources res,Post p){
        super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modify Post");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        TextField title_p = new TextField(p.getTitle_p(), "Post Title" , 20, TextField.ANY);
        TextArea desc = new TextField(p.getDescription_p(), "Post Description" , 20, TextField.ANY);
        
        title_p.setUIID("NewsTopLine");
        desc.setUIID("NewsTopLine");
        
        title_p.setSingleLineTextArea(true);
        desc.setSingleLineTextArea(true);
        Button btnModifer = new Button("Update");
        btnModifer.setUIID("Button");
        
        //Event onclick btnModifer        
        btnModifer.addPointerPressedListener(l -> {  
        p.setTitle_p(title_p.getText());
         p.setDescription_p(desc.getText());
        
        //CALLING THE UPDATE FUNCTION FROM SERVICECATEGORY
        
        if(ServicePost.getInstance().updatePost(p)){
            new PostListForm(res).show();
        }
        });
        Button btnAnnuler = new Button("Cancel");
        btnAnnuler.addActionListener(e ->{
            new PostListForm(res).show(); 
        });
        
        
        Label l1 = new Label();
          
        Container content = BoxLayout.encloseY(
                
                l1,
                new FloatingHint(title_p),
                new FloatingHint(desc),
                createLineSeparator(),
                btnModifer,
                btnAnnuler
        );
        
        add(content);
        show();
    }
 }
    

