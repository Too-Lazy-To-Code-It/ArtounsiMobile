/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIposts;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class FxmlpayementController implements Initializable {
    
    
    @FXML
    private TextField NumCart;
    @FXML
    private ChoiceBox<String> BoxMonth;
    @FXML
    private ChoiceBox<Integer> BoxYear;
    @FXML
    private TextField EMAIL;
    @FXML
    private TextField CVV;
    @FXML
    private TextField NomProp;
    /**
     * Initializes the controller class.
     */ 
    private  double  Montant_tot;
    private double amount;
    public void setAmount(double amount) {
    //this.Montant_tot = amount;
  this.amount = amount;
  
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    ObservableList<String> months = FXCollections.observableArrayList();
    for (int i = 1; i <= 12; i++) {
        String month = String.format("%02d", i);
        months.add(month);
    }
    BoxMonth.setItems(months);

    ObservableList<Integer> years = FXCollections.observableArrayList();
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    for (int year = 1900; year <= currentYear; year++) {
        years.add(year);
    }
    BoxYear.setItems(years);
    
}
    
    private boolean Vérifier(String numCart, int moisExp, int AnExp, int CVVC, double amount, String NomduPop) {
       
        
       Stripe.apiKey = "test";

       Map<String, Object> DataCard = new HashMap<>();
       DataCard.put("number", numCart);
       DataCard.put("exp_month", moisExp);
       DataCard.put("exp_year", AnExp);
       DataCard.put("cvc", CVVC);
       DataCard.put("name", NomduPop);
       Map<String, Object> tokenParams = new HashMap<>();
      tokenParams.put("card", DataCard);

      try {
        Token token = Token.create(tokenParams);

          Map<String, Object> DataCharge = new HashMap<>();
          DataCharge.put("amount", (int) (amount * 100));
          DataCharge.put("currency", "usd");
          DataCharge.put("description", "Test charge");
          DataCharge.put("source", token.getId());

          Charge charge = Charge.create(DataCharge);
          System.out.println("Charge effectuté avec succées");
          return true;
      } catch (StripeException e) {
          System.out.println("Charge echoué: " + e.getMessage());
          return false;
    }
      
}
    @FXML
    private void PayerProd(ActionEvent event) {
       
       String numCart = NumCart.getText();
       int moisExp = Integer.parseInt(BoxMonth.getValue());
       int AnExp = BoxYear.getValue();
       int CVVC = Integer.parseInt(CVV.getText());
       String email = EMAIL.getText();
       String NomduPop = NomProp.getText();
     System.out.println("Amount to be paid: " + amount); // print the amount
      Vérifier(numCart, moisExp, AnExp, CVVC, amount, NomduPop);
       
    
    
}
    
}