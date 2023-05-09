/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.AllUsers;
import com.mycompany.service.UserService;

public class SignUpForm extends BaseForm {

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");

        TextField Nametf = new TextField("", "Name", 20, TextField.ANY);
        TextField Birthdaytf = new TextField("", "Birthday", 20, TextField.ANY);
        TextField LastNametf = new TextField("", "LastName", 20, TextField.ANY);
        TextField Nationalitytf = new TextField("", "Nationality", 20, TextField.ANY);
        TextField Typetf = new TextField("", "Type", 20, TextField.ANY);
        TextField usernametf = new TextField("", "Username", 20, TextField.ANY);
        TextField emailtf = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField passwordtf = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPasswordtf = new TextField("", "Confirm Password", 20, TextField.PASSWORD);

        Nametf.setSingleLineTextArea(false);
        LastNametf.setSingleLineTextArea(false);
        Nationalitytf.setSingleLineTextArea(false);
        Typetf.setSingleLineTextArea(false);
        emailtf.setSingleLineTextArea(false);
        passwordtf.setSingleLineTextArea(false);
        confirmPasswordtf.setSingleLineTextArea(false);

        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");

        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(Nametf),
                createLineSeparator(),
                new FloatingHint(LastNametf),
                createLineSeparator(),
                new FloatingHint(Nationalitytf),
                createLineSeparator(),
                new FloatingHint(Typetf),
                createLineSeparator(),
                new FloatingHint(usernametf),
                createLineSeparator(),
                new FloatingHint(emailtf),
                createLineSeparator(),
                new FloatingHint(passwordtf),
                createLineSeparator(),
                new FloatingHint(confirmPasswordtf),
                createLineSeparator()
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        System.out.println(emailtf.getText());
        next.addActionListener(e -> {
            int $verif;
            AllUsers u = new AllUsers(Nametf.getText(), LastNametf.getText(), usernametf.getText(), emailtf.getText(), "2000-04-04", passwordtf.getText(), Nationalitytf.getText(), Typetf.getText());
                $verif = UserService.getInstance().Verif(emailtf.getText());
                new ActivateForm(res, u, $verif).show();
        });
    }

}
