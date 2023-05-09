
package com.mycompany.gui;

import com.mycompany.entites.Offre;
import com.mycompany.service.OffreService;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.util.Resources;
import java.util.List;


/**
 *
 * @author khaledguedria
 */
public class ShowForm extends Form {

    //var
    OffreService ts = OffreService.getInstance();
   //custom
       
       
     
       
public ShowForm( Resources res) {
    //custom
        this.getToolbar().addCommandToRightBar("Add offre", null, (e) -> new AddForm(res).show());
          this.getToolbar().addCommandToRightBar("Show my offers", null, (e) -> new Showmesoffres(res).show());
        this.setLayout(BoxLayout.y());
        this.setTitle("tous les offres");
      List<Offre> off=ts.fetchoffres();
          for (int i = 0; i < off.size(); i++) {
         
                        addItem(off.get(i));
        }

         this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
        new HomeForm(res).showBack();
    });
    }

  
    public void addItem(Offre f){

Container C1 = new Container (new BoxLayout (BoxLayout.Y_AXIS));
Container C2 = new Container (new BoxLayout (BoxLayout.Y_AXIS));
Container C3= new Container (new BoxLayout (BoxLayout.X_AXIS));
Container C4 = new Container (new BoxLayout (BoxLayout.X_AXIS));


      
Label  titre = new Label (f.getTitreoffre());
Label nickname = new Label (f.getNickname());

Label description = new Label (f.getDescriptionoffre());
Label type = new Label (f.getTypeoffre());
Label localisation = new Label (f.getLocalisationoffre());
Label categorie= new Label(f.getCategorieoffre());
  
     
C2.add(description);
C3.add(type);
C3.add(localisation);
C3.add(categorie);
C2.add(C3);
C1.add(titre);
C1.add(nickname);
C4.add(C1);
C4.add(C2);
this.add( C4);


       this.refreshTheme();
        }
}