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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Category;
import com.mycompany.entites.Comment;
import com.mycompany.service.ServiceCategory;
import com.mycompany.service.ServiceComment;

/**
 *
 * @author amine
 */
public class UpdateCommentNewsfeedForm extends BaseForm{
    
    Form current;
    public UpdateCommentNewsfeedForm(Resources res, Comment c ){
     super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Edit Comment");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        TextField Comment = new TextField(c.getComment(), "Comment" , 20, TextField.ANY);
        
        Comment.setUIID("NewsTopLine");
        
        Comment.setSingleLineTextArea(true);
        
        Button btnModifer = new Button("Update");
        btnModifer.setUIID("Button");
        
        //Event onclick btnModifer        
        btnModifer.addPointerPressedListener(l -> {
        c.setComment(Comment.getText());   
        
        
        //CALLING THE UPDATE FUNCTION FROM SERVICECATEGORY
        
        if(ServiceComment.getInstance().updateComment(c)){
            new NewsfeedForm(res).show();
        }
        });
        Button btnAnnuler = new Button("Cancel");
        btnAnnuler.addActionListener(e ->{
            new NewsfeedForm(res).show(); 
        });
        
        
        Label l1 = new Label();
          
        Container content = BoxLayout.encloseY(
                
                l1,
                new FloatingHint(Comment),
                createLineSeparator(),
                btnModifer,
                btnAnnuler
        );
        
        add(content);
        show();
    }
}
