/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import java.io.IOException;

import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Logged;
import service.PanierService;

/**
 * FXML Controller class
 *
 * @author aouad
 */
public class FXML_PayementController implements Initializable {
   PanierService pn = new PanierService();

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
   
    
   private  double  Montant_tot;
    void setMontant(double Montant_tot) {
         this.Montant_tot = Montant_tot;
    }

   

    /**
     * Initializes the controller class.
     */
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

    private boolean Vérifier(String numCart, int moisExp, int AnExp, int CVVC, double Montant_tot, String NomduPop) {
       Stripe.apiKey = "sk_test_51MiOyPHjp8SAtqB82djmo7hRd9lvbM7i9MEBNsA9gJVxFhhEDLoL5pyTANP176euOnp6Zrk1ooSGVo3rJY8Qwo2a00gYTeyxb1";

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
          DataCharge.put("amount", (int) (Montant_tot * 100));
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
    private void PayerProd(ActionEvent event) throws IOException {
       String numCart = NumCart.getText();
       int moisExp = Integer.parseInt(BoxMonth.getValue());
       int AnExp = BoxYear.getValue();
       int CVVC = Integer.parseInt(CVV.getText());
       String email = EMAIL.getText();
       String NomduPop = NomProp.getText();
     System.out.println("LLLLLLLLLLLL"+Montant_tot);
      if (Vérifier(numCart, moisExp, AnExp, CVVC, Montant_tot, NomduPop)) {
        //pn.viderPanier(Logged.get_instance().getUser().getID_User());
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXML_SuccesPayment.fxml"));
        Parent view=loader.load();
        Scene scene = new Scene(view);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    }


    
  
        
        
    }
    

