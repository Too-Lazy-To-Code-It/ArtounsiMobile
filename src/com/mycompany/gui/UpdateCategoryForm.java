/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Category;
import com.mycompany.service.ServiceCategory;

/**
 *
 * @author amine
 */
public class UpdateCategoryForm extends BaseForm{
    
    Form current;
    public UpdateCategoryForm(Resources res, Category c ){
      super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add Category");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        TextField name_category = new TextField(c.getName_category(), "Category Name" , 20, TextField.ANY);
        
        name_category.setUIID("NewsTopLine");
        
        name_category.setSingleLineTextArea(true);
        
        Button btnModifer = new Button("Update");
        btnModifer.setUIID("Button");
        
        //Event onclick btnModifer        
        btnModifer.addPointerPressedListener(l -> {
        c.setName_category(name_category.getText());   
        
        
        //CALLING THE UPDATE FUNCTION FROM SERVICECATEGORY
        
        if(ServiceCategory.getInstance().updateCategory(c)){
            new CategoryListForm(res).show();
        }
        });
        Button btnAnnuler = new Button("Cancel");
        btnAnnuler.addActionListener(e ->{
            new CategoryListForm(res).show(); 
        });
        
        
        Label l1 = new Label();
          
        Container content = BoxLayout.encloseY(
                
                l1,
                new FloatingHint(name_category),
                createLineSeparator(),
                btnModifer,
                btnAnnuler
        );
        
        add(content);
        show();
    }
}
