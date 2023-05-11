/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.LignePanier;
import models.Panier;
import service.LignePanierService;
import service.PanierService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * FXML Controller class
 *
 * @author aouad
 */
public class FXML_AfficherPanierController implements Initializable {
    LignePanierService lp = new LignePanierService();
    PanierService pn=new PanierService();
    Panier pan=new Panier();
    LignePanier lignepan = new LignePanier();
    private FXML_AfficherPanierController afficherPanierController;
    private int idPanier;
   

    private TableColumn<?, ?> prixUn;

    @FXML
    private Button vider;
    @FXML
    private Label montant_total;
    private Button modifier;
    @FXML
    private Label nbr_prod;
    @FXML
    private Button retour;
    @FXML
    private Button imprimer;
    private TableColumn<?, ?> date;
    private TableColumn<?, ?> nom;
    @FXML
    private ListView<LignePanier> tablePanier2;
    ObservableList<LignePanier> list = FXCollections.observableArrayList();
    @FXML
    private Button pay;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane gridPan;
    @FXML
    private AnchorPane anchorePan;

 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idPanier=8;
        lp.afficheligne(idPanier).stream().forEach(a->list.add(a));
//        tablePanier2.setItems(list);
        
         int column=0;
       int row = 1 ;
       for (int i = 0; i < list.size(); i++)
       {
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("FXML_CartePanier.fxml"));
               anchorePan = fxmlLoader.load();
               FXML_CartePanierController CartPanController = fxmlLoader.getController();
               lignepan = list.get(i);
               CartPanController.setDataPanier(lignepan);
               
               
             if (column == 2) {
                    column = 0;
                    row++;
                }
                gridPan.add(anchorePan, column++, row);
             
                //set grid width
                gridPan.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPan.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPan.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridPan.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPan.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPan.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorePan, new Insets(10));
                GridPane.setColumnIndex(anchorePan, column);
           } catch (IOException ex) {
               Logger.getLogger( FXML_AfficherPanierController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
  
         double montantTotal = pn.calculerMontantTotal(idPanier);
         montant_total.setText(String.valueOf(montantTotal));
         int nbrProd = pn.calculerNombreProduits(idPanier);
         pn.MisàjourMontantTotal(idPanier,montantTotal);
         nbr_prod.setText(String.valueOf(nbrProd));
         montant_total.setText(String.valueOf(montantTotal));
         Mtotal(String.valueOf(montantTotal));
         
    }    
    
   public void setIdPanier(int idPanier) {
       this.idPanier = idPanier;
     
    }

        public void  Mtotal(String total) {
          this.montant_total.setText(total);
        } 

    public void initializePanier(int idPanier) {
      this.idPanier = idPanier;

   }

    @FXML
    private void vider(ActionEvent event) {
        pn.viderPanier();
    }

  

    @FXML
    private void GotoAccueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Acceuil.fxml"));
        Parent root = loader.load();
        retour.getScene().setRoot(root);
    }

    @FXML
  private void imprimer(ActionEvent event) throws FileNotFoundException, DocumentException, BadElementException, IOException {
    String file_fact = ("C:\\Users\\aouad\\Downloads\\Facture.pdf");
    Document doc = new Document();
    PdfWriter.getInstance(doc, new FileOutputStream(file_fact));
    doc.open();
    
     // Ajout d'une image
    Image image = Image.getInstance("C:\\xampp\\htdocs\\img\\logo.png");
    image.scaleToFit(100,100); // Ajuster la taille de l'image
    doc.add(image);


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
    List<LignePanier> lignePanierList = lp.getLignePanierparIdPanier(idPanier);
    for (LignePanier lignePanier : lignePanierList) {
        //cell = new PdfPCell(new Paragraph(lignePanier.getNomProd()));
       // table.addCell(cell);
        cell = new PdfPCell(new Paragraph(String.valueOf(lignePanier.getDateajout())));
        table.addCell(cell);
      //  cell = new PdfPCell(new Paragraph(String.valueOf(lignePanier.getPrix_unitaire())));
       // table.addCell(cell);
    }
    doc.add(table);
    doc.add(new Paragraph("Le nombre des produits commandés : " + pn.calculerNombreProduits(idPanier)));
    doc.add(new Paragraph("Montant total à payer en DT : " + pn.calculerMontantTotal(idPanier)));
    doc.close();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Success");
               alert.setHeaderText("PDF Downloaded");
               alert.setContentText("The PDF has been downloaded successfully.");
               alert.showAndWait();
//    FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save PDF File");
//        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
//
//        if (file != null) {
//            try {
//                FileInputStream inputStream = new FileInputStream("recuCommande.pdf");
//                OutputStream outputStream = new FileOutputStream(file);
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = inputStream.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, length);
//                }
//                inputStream.close();
//                outputStream.close();
//
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Success");
//                alert.setHeaderText("PDF Downloaded");
//                alert.setContentText("The PDF has been downloaded successfully.");
//                alert.showAndWait();
//            } catch (IOException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("File Error");
//                alert.setContentText("An error occurred while reading or writing the file.");
//                alert.showAndWait();
//            }
}
    
    


    @FXML
    private void payer(ActionEvent event) throws IOException {
         // prendre le montant total à payer
        double Montant_tot = pn.calculerMontantTotal(idPanier);
       
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXML_Payement.fxml"));
        Parent view_2=loader.load();
        FXML_PayementController paymController=loader.getController();
        paymController.setMontant(Montant_tot);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
     
    }
    
   
    }
    
      
    

