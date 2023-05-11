package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

public class HomeForm extends Form {
    public HomeForm(Resources res) {
        //custom
        this.setLayout(BoxLayout.yCenter());
        this.setTitle("Home");

        //widgets
        Button showTaskBtn = new Button("Show offres");

        //actions
        showTaskBtn.addActionListener((evt) -> {
            new ShowForm(res).show();
        });

        //end
        Toolbar tb = this.getToolbar();
        tb.getStyle().setBgColor(0x6A0888); // set violet color
        tb.getStyle().setFgColor(0xFFFFFF); // set text color to white   b ^p:
        tb.addCommandToOverflowMenu("Settings", null, (evt) -> {
            // handle settings command
        });
        tb.addComponentToSideMenu(showTaskBtn);
    }
}
