/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import models.LignePanier;
import models.Logged;
import service.LignePanierService;
import service.PanierService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_SuccesPaymentController implements Initializable {

    @FXML
    private Button fact;
    LignePanierService lp = new LignePanierService();
    PanierService pn=new PanierService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Télécharger_Facture(ActionEvent event) throws FileNotFoundException, DocumentException {
         String file_fact = ("C:\\Users\\achref\\Desktop\\Factures Wiem pidev\\Facture.pdf");
    Document doc = new Document();
    PdfWriter.getInstance(doc, new FileOutputStream(file_fact));
    doc.open();
    
     // Ajout d'une image
   // Image image = Image.getInstance("C:\\xampp\\htdocs\\img\\logo.png");
   // image.scaleToFit(100,100); // Ajuster la taille de l'image
  //  doc.add(image);


    PdfPTable table = new PdfPTable(3);
    table.setWidthPercentage(100);
    table.getDefaultCell().setPadding(5);
    table.getDefaultCell().setBorderWidth(1);
    table.getDefaultCell().setBorderColor(BaseColor.BLACK);
    
    PdfPCell cell;
    cell = new PdfPCell(new Paragraph("NomProd"));
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph("Date d' Ajout"));
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph("Prix_unitaire"));
    table.addCell(cell);
    List<LignePanier> lignePanierList = lp.AfficherPanierbyiduser(Logged.get_instance().getUser().getID_User());
    for (LignePanier lignePanier : lignePanierList) {
        cell = new PdfPCell(new Paragraph(lignePanier.getProduit().getNom()));
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(String.valueOf(lignePanier.getDateajout())));
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(String.valueOf(lignePanier.getProduit().getPrix())));
        table.addCell(cell);
    }
    doc.add(table);
    doc.add(new Paragraph("Le nombre des produits commandés : " + pn.calculerNombreProduits(Logged.get_instance().getUser().getID_User())));
    doc.add(new Paragraph("Montant total à payer en DT : " + pn.calculerMontantTotal(Logged.get_instance().getUser().getID_User())));
    doc.close();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Success");
               alert.setHeaderText("PDF Downloaded");
               alert.setContentText("The PDF has been downloaded successfully.");
               alert.showAndWait();
               
               pn.viderPanier(Logged.get_instance().getUser().getID_User());
        
    }
    
}
