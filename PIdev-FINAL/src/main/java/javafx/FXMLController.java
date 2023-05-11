/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
   
/**
 * FXML Controller class
 *
 * @author nour2
 */
public class FXMLController implements Initializable {
Set<String>words = new HashSet<>();
private AutoCompletionBinding<String> autocomplete;
 
    @FXML
    private TextField ok;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   autocomplete=TextFields.bindAutoCompletion(ok, words );
     ok.setOnKeyPressed((KeyEvent e)->{
          switch(e.getCode())
          {case ENTER:
              learnWord(ok.getText());
              break;
          default:break;  
          }
      });
 
}private void learnWord(String text) {
       words.add(text);
       if( autocomplete!=null)
       { autocomplete.dispose();}
         autocomplete=TextFields.bindAutoCompletion(ok, words );
    }}