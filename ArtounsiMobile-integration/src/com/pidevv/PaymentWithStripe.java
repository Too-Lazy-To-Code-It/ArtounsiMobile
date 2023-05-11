/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pidevv;

import com.codename1.ui.Dialog;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aouad
 */
public class PaymentWithStripe {
        public static boolean creditcardvalid;

        public boolean verifyCardAndPay(String cardNumber, int expMonth, int expYear, String cvc , String cardholderName ,float montant ) throws StripeException {


            Stripe.apiKey = "sk_test_51MiOyPHjp8SAtqB82djmo7hRd9lvbM7i9MEBNsA9gJVxFhhEDLoL5pyTANP176euOnp6Zrk1ooSGVo3rJY8Qwo2a00gYTeyxb1";

            // Create a test token for a Visa card

            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("number", cardNumber);
            cardParams.put("exp_month", expMonth);
            cardParams.put("exp_year", 24);
            cardParams.put("cvc", cvc);
            cardParams.put("name", cardholderName);
            Map<String, Object> tokenParams = new HashMap<>();
            tokenParams.put("card", cardParams);
            System.out.print("STRIPPPE"+montant);
        int montantEnCentimes = (int)((montant) * 100);

       
            try {

                Token token = Token.create(tokenParams);
                // Use the test token ID to create a charge
                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount",montantEnCentimes ); // Charge amount in cents ya3ni namel *100
                chargeParams.put("currency", "usd"); // Charge currency
                chargeParams.put("description", "Test charge");
                chargeParams.put("source", token.getId());

                Charge charge = Charge.create(chargeParams);
                
                System.out.println("Charge succeeded!");
                  
                 if (creditcardvalid=true) {
                Dialog.show("Success", "Votre paiement a été effectué avec succès", "Got it", null);
              
            } else {
                Dialog.show("Failed", "Essayer à nouveau", "Got it", null);
            }
            } catch (StripeException e) {
                creditcardvalid=false;
                System.out.println("Charge failed: " + e.getMessage());
            }


            return creditcardvalid;
        }
}
