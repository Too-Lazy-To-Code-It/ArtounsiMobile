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
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.pidevv.PaymentWithStripe;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Sign in UI
 *
 * @author Shai Almog
 */
public class PaymentForm extends BaseForm {

    public PaymentForm(Resources res) {
        super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
//        add(BorderLayout.NORTH, new Label(res.getImage("Artounsi LOGO.png"), "LogoLabel"));
        
//        TextField username = new TextField("", "Username", 20, TextField.ANY);
//        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);

        TextField nameTF = new TextField("", "first name", 50, TextField.ANY);
        TextField numberTF = new TextField("", "code ", 50, TextField.PASSWORD);


String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

// Créer une liste des années à partir de 1998 jusqu'à l'année en cours
Calendar cal = Calendar.getInstance();
int currentYear = 2050;
String[] years = new String[currentYear - 1998 + 1];
for (int i = 0; i < years.length; i++) {
    years[i] = String.valueOf(1998 + i);
}


// Créer les ComboBox pour les mois et les années
ComboBox<String> expmonthCB = new ComboBox<>(months);
ComboBox<String> expyearCB = new ComboBox<>(years);



  TextField cvcTF = new TextField("", "CVC", 50, TextField.ANY);
       
     
   
        nameTF.setSingleLineTextArea(false);
        numberTF.setSingleLineTextArea(false);
        cvcTF.setSingleLineTextArea(false);
      
        Button signIn = new Button("Confirmer le paiement");
                //actions
        signIn.addActionListener((evt) -> {
            try{

                if(nameTF.getText().isEmpty() || expmonthCB.getSelectedItem().isEmpty() || expyearCB.getSelectedItem().isEmpty()|| numberTF.getText().isEmpty()|| cvcTF.getText().isEmpty()){
                      
                    Dialog.show("Veuillez verifier les données","", "Annuler" , "OK");
                }
                else if (isValidName(nameTF.getText())==false||isValidName(expmonthCB.getSelectedItem())==false||isValidName(expyearCB.getSelectedItem())==false||isValidName(numberTF.getText())==false||isValidName(cvcTF.getText())==false){
                    Dialog.show("Please don't use special caracteres ","", null , "OK");
                }else if (numberTF.getText().length()!= 16 ) {
                 Dialog.show("code number must contain 16 numbers ","", null , "OK");
                } else if (cvcTF.getText().length()!=3) {
                 Dialog.show("Please CVC must contain 3 numbers ","", null , "OK" );
                }else if (expyearCB.getSelectedItem().length()!=4) {
                 Dialog.show("Please expyear must contain 2 numbers ","", null , "OK" );
                }else if (expmonthCB.getSelectedItem().length()!=2) {
                 Dialog.show("Please expmonth must contain 2 numbers ","", null , "OK" );
                }
                else {
                    InfiniteProgress ip = new InfiniteProgress();//Loading after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    PaymentWithStripe Paystr =new PaymentWithStripe();
                    
                  
                    
                    boolean transaction = Paystr.verifyCardAndPay(numberTF.getText(), Integer.valueOf(expmonthCB.getSelectedItem()), Integer.valueOf(expyearCB.getSelectedItem()), String.valueOf(cvcTF.getText()), nameTF.getText(), (float) (AfficherPanier.Montant_total *100));

                    System.out.println("AFFFFF"+AfficherPanier.Montant_total);
                  
                    
                    iDialog.dispose(); //nahiw loading baad maamalna ajout 
                    if(transaction ==true){
                        new AfficherPanier(res).show();
                    Dialog.show("Payment sucesseded !","", null, "OK" );
                    }else{
                    Dialog.show("check you're credientiels !","", null, "OK" );
                    }

                    
                    
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        });
          Container content = BoxLayout.encloseY(
                new Label("Effectuer le Paiement", "LogoLabel"),
                new FloatingHint( nameTF),
                createLineSeparator(),
                new FloatingHint( numberTF),
                createLineSeparator(),
                new FloatingHint(cvcTF),
                createLineSeparator(),
                expmonthCB,
                expyearCB
                
               
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
              signIn
        ));
   
    }
    
       private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));

    }
    public static boolean isValidName(String name) {
         List<Character> specialCharacters = Arrays.asList(
                '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '{', '}', '[', ']', '\\', '|', ';', ':', '\'', '\"', ',', '.', '/', '?', '<', '>', '~', '`'
        );
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (specialCharacters.contains(c)) {
                return false;
            }
        }
        return true;
    } 
    
    
    
    
    
    
}
