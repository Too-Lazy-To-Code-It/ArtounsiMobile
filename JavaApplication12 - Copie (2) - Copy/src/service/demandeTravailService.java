/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import interfaces.demandeTravailInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.Category;
import models. AllUsers;
import models.demandeTravail;
import util.MaConnexion;

public class demandeTravailService implements demandeTravailInterface {
       //var
    Connection cnx = MaConnexion.getInstance().getCnx();
     AllUsers studio = new  AllUsers();

    //********************verifier les informations ajoutés d'offre de travail*****************************
    
     public Boolean grosMots(String words) {
        grosMotsService gros= new  grosMotsService ();
        List<String> listBadWords = gros.fetchgrosmotsString();
        String badWord = "";
        Boolean existe = false;
        String allbadwords = "";
        for (String str : listBadWords) {
            if (words.toLowerCase().contains(str)) {
                badWord += "" + str;
                if (str.length() >= 1) {
                    StringBuilder badWordHiden = new StringBuilder();
                    badWordHiden.append(str.charAt(0));
                    for (int i = 0; i < str.length() - 2; ++i) {
                        badWordHiden.append("*");
                    }
                    badWordHiden.append(str.charAt(str.length() - 1));
                    str = badWordHiden.toString();
                    if (!str.isEmpty()) {
                        existe = true;
                        allbadwords += badWordHiden + "  ";
                    }
                }
            }
        }
        if (!allbadwords.isEmpty()) {
              Alert alert = new Alert(Alert.AlertType.WARNING);
alert.setTitle("Alert");
alert.setHeaderText(null);
alert.setContentText("ATTENTION !! Vous avez écrit un gros mot  : " + allbadwords + " .C'est un avertissement ! Priére d'avoir un peu de respect ! ");

alert.showAndWait();

            System.out.println("ATTENTION !! Vous avez écrit un gros mot  : " + allbadwords + " .C'est un avertissement ! Priére d'avoir un peu de respect ! ");
        }
        return existe;
    }
//*******************l'ajout de demannde******************************
     public static boolean verifajoutdemande=false;
@Override
   public void addDemande(demandeTravail d) {
       AllUsers artiste = new  AllUsers();
        //ajouter l'utilisateur connecter pour recuperer ces informations qui doit etre ajouter par la suite dans la table offretravail
        try {
 String req2 = "SELECT * FROM `allusers` WHERE ID_User= \'" + d.getIdArtiste() + "\' and Type= 'Artist'";
            Statement st1 = cnx.createStatement();
            ResultSet rs1 = st1.executeQuery(req2);
            while (rs1.next()) {

                artiste.setID_User(rs1.getInt(1));
                artiste.setName(rs1.getString(2));
                artiste.setEmail(rs1.getString(4));
                artiste.setDescription(rs1.getString(13));
                 artiste.setNickname(rs1.getString(10));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // ensuite ajouter le date systeme*********************************
        Date date = new Date();
       Timestamp sqldate = new java.sql.Timestamp(date.getTime());

        
        try {
            //verifier si l'offre est deja ajouter par le studio conencter ou non en collecton les offre qui l  id studio et titre doffre
            String req3 = "SELECT * FROM `demandetravail2` WHERE ID_User= \'" + d.getIdArtiste()+ "\'and titreDemande=\'" + d.getTitreDemande() + "\'";
            Statement st3 = cnx.createStatement();
            ResultSet rs3 = st3.executeQuery(req3);
            if (grosMots(d.getTitreDemande()) || grosMots(d.getDescriptionDemande())) {
            } else if (rs3.next()) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("vous avez deja publier la demande:  \'" + d.getTitreDemande()+ "\' !");

                alert.showAndWait();
                System.out.println("vous avez deja publier la demande:  \'" + d.getTitreDemande()+ "\' !");
            } else {
                //verifier les gros mots*******************************
                if (d.getTitreDemande().equals("") || d.getDescriptionDemande().equals("") || d.getCategorieDemande().equals("") ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("veuiller remplir tous les champs");
                    alert.showAndWait();
                } else{
                //l'ajout valider de l'offre****************************************************
                String req = "INSERT INTO `demandeTravail2`(`ID_User`,`Nickname`,`titreDemande`, `descriptionDemande`, `categorieDemande`,`idCategorie`,`dateAjoutDemande`,`pdf`) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, d.getIdArtiste());
                ps.setString(2, artiste.getNickname());
                ps.setString(3, d.getTitreDemande());
                ps.setString(4, d.getDescriptionDemande());
         ps.setString(5, d.getCategorieDemande().getName_category());
           ps.setInt(6, d.getCategorieDemande().getId_Category());
                ps.setTimestamp(7, sqldate);
                  ps.setString(8, d.getPdf());
                ps.executeUpdate();
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("demande de travail ajouté");

                    alert.showAndWait();
                System.out.println("demande de travail ajouté");
                verifajoutdemande=true;
                } }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
//**********************afficher tous les demandes*******************************
    @Override
    public List<demandeTravail> fetchDemandesPerDate() {
      List<demandeTravail> demandesTravail = new ArrayList<>();
        try {

            String req = "SELECT * FROM demandeTravail2  ORDER BY dateAjoutDemande DESC";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                demandeTravail dm = new demandeTravail();
                dm.setIdDemande(rs.getInt(7));
                dm.setIdArtiste(rs.getInt(1));
                dm.setNomArtiste(rs.getString(2));
                dm.setTitreDemande(rs.getString(3));
                dm.setDescriptionDemande(rs.getString(4));
                 Category c = new Category(rs.getInt(8),rs.getString(5));
                dm.setCategorieDemande(c);
                dm.setDateAjoutDemande(rs.getTimestamp(6));
                 dm.setPdf(rs.getString("pdf"));
                demandesTravail.add(dm);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return demandesTravail;
    }
//***************afficher les demandes de l'utilisateur connecté************************
    @Override
    public List<demandeTravail> fetchDemandesPerIdDate(int id) {
       List<demandeTravail> demandesTravail = new ArrayList<>();

        try {

            String req = "SELECT * FROM `demandetravail2` WHERE ID_User= \'" + id + "\' ORDER BY dateAjoutDemande DESC";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                demandeTravail dm = new demandeTravail();
                dm.setIdDemande(rs.getInt(7));
                dm.setIdArtiste(rs.getInt(1));
                dm.setNomArtiste(rs.getString(2));
                dm.setTitreDemande(rs.getString(3));
                dm.setDescriptionDemande(rs.getString(4));
                 Category c = new Category(rs.getInt(8),rs.getString(5));
                dm.setCategorieDemande(c);
                dm.setDateAjoutDemande(rs.getTimestamp(6));
                 dm.setPdf(rs.getString("pdf"));
                demandesTravail.add(dm);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return demandesTravail;
    }

   
//**********************afficher demande par son id *********************************
        @Override
    public  demandeTravail fetchdemandeParId(int id) {
        demandeTravail demande = new demandeTravail();
        try {
            String req = "SELECT * FROM `demandetravail2` WHERE idDemande= \'" + id + "\' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                demande.setIdDemande(rs.getInt("idDemande"));
                demande.setIdArtiste(rs.getInt("ID_User"));
                demande.setNomArtiste(rs.getString("Nickname"));
                demande.setTitreDemande(rs.getString("titreDemande"));
                demande.setDescriptionDemande(rs.getString("descriptionDemande"));
                Category c = new Category(rs.getInt(8),rs.getString(5));
                demande.setCategorieDemande(c);
                demande.setDateAjoutDemande(rs.getTimestamp(6));
                demande.setPdf(rs.getString("pdf"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
         return demande;
          }
    //*********************** modifier demande***************************************
    public static boolean verifdemande=false;
    @Override
    public void modifierDemande(demandeTravail d) {
     
        
        try {
            //verifier si l'offre est deja ajouter par le studio conencter ou non en collecton les offre qui l  id studio et titre doffre
            String req3 = "SELECT COUNT(*)FROM `demandetravail2` WHERE ID_User= \'" + d.getIdArtiste()+ "\'and titreDemande=\'" + d.getTitreDemande() + "\'";
            Statement st3 = cnx.createStatement();
            ResultSet rs3 = st3.executeQuery(req3);
            if (grosMots(d.getTitreDemande()) || grosMots(d.getDescriptionDemande())) {
            } else if (rs3.next() && rs3.getInt(1) > 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("vous avez deja publier la demande \'" + d.getTitreDemande()+ "\' veuillez changer le titre!");

                alert.showAndWait();
                
            } else {
                if (d.getTitreDemande().equals("") || d.getDescriptionDemande().equals("") ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("veuiller remplir tous les champs");
                    alert.showAndWait();
                } else if (d.getTitreDemande().trim().length() == 0||d.getDescriptionDemande().trim().length() == 0)
                { Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Attention, il y'a un champs vide");
                    alert.showAndWait();} else
                {
                    String req = "update `demandetravail2` set titreDemande=?,descriptionDemande=?,categorieDemande=?,idCategorie=?,pdf=?  where idDemande=?";
                    PreparedStatement pst = cnx.prepareStatement(req);
                    pst.setString(1, d.getTitreDemande());
                    pst.setString(2, d.getDescriptionDemande());
                    pst.setString(3,  d.getCategorieDemande().getName_category());
                    pst.setInt(4,  d.getCategorieDemande().getId_Category());
                      pst.setString(5,  d.getPdf());
                    pst.setInt(6, d.getIdDemande());
                    pst.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("demande de travail modifié  avec succès");
               verifdemande=true;
                    alert.showAndWait();
                    System.out.println("demande de travail modifié  avec succès");
                    
                } } 
        } catch (SQLException ex) {
            Logger.getLogger(demandeTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//******************************supprimer demande****************************
    @Override
    public void SupprimerDemande(demandeTravail d) {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM `demandetravail2` WHERE idDemande= " + d.getIdDemande() + "";
            st.executeUpdate(req);
           
          
           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
  //verifier si loffre existe deja dans la liste de resultat de recherche ou non poureviter la redondance
    public boolean containsId(List<demandeTravail> list, int id) {
        return list.stream().filter(o -> o.getIdDemande() == (id)).findFirst().isPresent();
    }
    //************************chercher demande***************************************
    @Override
    public List<demandeTravail> chercherDemande(String mots) {
      List<demandeTravail> demandesTravailtrouver = new ArrayList<>();
        try {
            String[] words = mots.split(" ");
            String req;
            for (String motss : words) {
                req = "SELECT * FROM `demandetravail2` WHERE  titreDemande like '%" + motss + "%' or Nickname like '%" + motss + "%' or descriptionDemande like '%" + motss + "%' or categorieDemande like '%" + motss + "%'";
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    demandeTravail dm = new demandeTravail();
                    dm.setIdDemande(rs.getInt(7));
                    dm.setIdArtiste(1);
                    dm.setTitreDemande(rs.getString(3));
                    dm.setDescriptionDemande(rs.getString(4));
                    dm.setNomArtiste(rs.getString(2));
                        Category c = new Category();
                      c.setId_Category(rs.getInt("idCategorie"));
                      c.setName_category("categorieDemande");
                    dm.setCategorieDemande(c);
                    if (!containsId( demandesTravailtrouver, dm.getIdDemande())) {
                         demandesTravailtrouver.add(dm);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return  demandesTravailtrouver;
    }
//********************** contacter l'artiste par mail  qui a publier une demande
    @Override
    public void contacterViaMail( int idStudio, demandeTravail d) {
         String mailreceiver="";
        try {
            System.out.println("preparing");
            AllUsers studio = new  AllUsers();
            //recuperer le nom dartiste proprietaire de demande***************************
            String req1= "SELECT Nickname  FROM `demandeTravail2` WHERE idDemande= \'" + d.getIdDemande()+ "\' ";
            Statement st5 = cnx.createStatement();
            ResultSet rs5 = st5.executeQuery(req1);
            while (rs5.next()) {
                d.setNomArtiste(rs5.getString(1));
            }
          //recuperer le mail du studio proprietaire de nickname*****************************
            String req6="SELECT Email FROM `allusers` WHERE Nickname= \'" + d.getNomArtiste()+ "\' ";
             Statement st6 = cnx.createStatement();
            ResultSet rs6 = st6.executeQuery(req6);
            while (rs6.next()) {
               mailreceiver=rs6.getString(1);
            }
            //ajouter l'utilisateur connecter li howa studio li bch ycontactih*******************************
            try {
                String req2 = "SELECT * FROM `allusers` WHERE ID_User= \'" + idStudio + "\' and Type= 'Studio'";
                Statement st1 = cnx.createStatement();
                ResultSet rs1 = st1.executeQuery(req2);
                while (rs1.next()) {
                    studio.setID_User(rs1.getInt(1));
                    studio.setNickname(rs1.getString(9));
                    studio.setEmail(rs1.getString(4));
                    studio.setDescription(rs1.getString(10));
                    //nitialiser le mail******************************************************
                    Properties properties = new Properties(); //njmou nhot ufih cle valeur
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");
                    String myAccountEmail = "nourelhoudachawebi@gmail.com";
                    String password = "tvblhpbflfgrplob";// enabled Two phase authentication for my Google account and as a result im usingthis password
                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(myAccountEmail, password);
                        }
                    });
                    //envoyer le mail*************************************************
                    Message message = prepareMessage(session, myAccountEmail, mailreceiver, d, studio.getNickname(), studio.getDescription(), studio.getEmail());
                    Transport.send(message);
                    System.out.println("votre demande est envoyé avec succes");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (MessagingException ex) {
                Logger.getLogger(demandeTravailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(demandeTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //preparation du msg*************************************************
    private static Message prepareMessage(Session session, String myAccountEmail, String mailreceiver, demandeTravail d, String nomstudio, String decriptionstudio, String mail) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailreceiver));
            message.setSubject("1nouvelle Studio est  interessé par votre demande : " + d.getTitreDemande()+ " ");
            // message.setText("hey there,\n");
            String htmlCode = "<body style=\"background-color:url('https://images.unsplash.com/photo-1500462918059-b1a0cb512f1d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80')\">\n"
                    + "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"550\" bgcolor=\"white\" style=\"border:2px solid black\">\n"
                    + "        <tbody>\n"
                    + "            <tr>\n"
                    + "                <td align=\"center\">\n"
                    + "                    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"col-550\" width=\"550\">\n"
                    + "                        <tbody>\n"
                    + "                            <tr>\n"
                    + "                                <td align=\"center\" style=\"background-color: #C10C99;\n"
                    + "										height: 50px;\">\n"
                    + "\n"
                    + "                                    <a href=\"#\" style=\"text-decoration: none;\">\n"
                    + "                                        <p style=\"color:white;\n"
                    + "												font-weight:bold;\">\n"
                    + "                                            arTounsi\n"
                    + "                                        </p>\n"
                    + "                                    </a>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                        </tbody>\n"
                    + "                    </table>\n"
                    + "                </td>\n"
                    + "            </tr>\n"
                    + "            <tr style=\"height: 300px;\">\n"
                    + "                <td align=\"center\" style=\"border: none;\n"
                    + "						border-bottom: 2px solid #1C233D;\n"
                    + "						padding-right: 20px;padding-left:20px;background-color: #1C233D\">\n"
                    + "\n"
                    + "                    <p style=\" font-weight: bolder;font-size: 42px; letter-spacing: 0.025em; color:white;\">\n"
                    + "                       Bonjour " + d.getNomArtiste()+ "!\n"
                    + "                    </p>\n"
                    + "                </td>\n"
                    + "            </tr>\n"
                    + "\n"
                    + "            <tr style=\"display: inline-block;\">\n"
                    + "                <td style=\"height: 150px;\n"
                    + "						padding: 20px;\n"
                    + "						border: none;\n"
                    + "						border-bottom: 2px solid #361B0E;\n"
                    + "						background-color: #1C233D; \">\n"
                    + "\n"
                    + "                    <h2 style=\"text-align: left;\n"
                    + "							align-items: center; color:white\">\n"
                    + "                         Le studio " + nomstudio + "  \n  decriver par  :" + decriptionstudio + " \n  avec le mail " + mail + " \n"
                    + " `\n  est interessé par votre demande :  (" + d.getTitreDemande()+ ")"
                    + "                    </h2>\n"
                    + "                    <p class=\"data\" style=\"text-align: justify-all;\n"
                    + "							align-items: center;\n"
                    + "							font-size: 15px;\n"
                    + "							padding-bottom: 12px;color:white\">\n"
                    + "                       Description de de demande :  " + d.getDescriptionDemande()+ "\n"
                    + "                    </p>\n"
                    + "\n"
                    + "                </td>\n"
                    + "            </tr>\n"
                    + "            <tr style=\"border: none;\n"
                    + "			background-color: #C10C99;\n"
                    + "			height: 40px;\n"
                    + "			color:white;\n"
                    + "			padding-bottom: 20px;\n"
                    + "			text-align: center;\">\n"
                    + "\n"
                    + "                <td height=\"40px\" align=\"center\">\n"
                    + "                    <p style=\"color:white;\n"
                    + "	line-height: 1.5em;\">\n"
                    + "                        arTounsi\n"
                    + "                    </p>\n"
                    + "\n"
                    + "\n"
                    + "                    <a href=\"#\" style=\"border:none;\n"
                    + "	text-decoration: none;\n"
                    + "	padding: 5px;\">\n"
                    + "\n"
                    + "                        <img height=\"20\" src=\"https://extraaedgeresources.blob.core.windows.net/demo/salesdemo/EmailAttachments/facebook-letter-logo_20190610100050.png\" width=\"24\" style=\"position: relative;\n"
                    + "			padding-bottom: 5px;\" />\n"
                    + "                    </a>\n"
                    + "                </td>\n"
                    + "            </tr>\n"
                    + "\n"
                    + "        </tbody>\n"
                    + "    </table>\n"
                    + "    </td>\n"
                    + "    </tr>\n"
                    + "    <tr>\n"
                    + "        <td class=\"em_hide\" style=\"line-height:1px;\n"
                    + "				min-width:700px;\n"
                    + "				background-color:#ffffff;\">\n"
                    + "            <img alt=\"\" src=\"images/spacer.gif\" style=\"max-height:1px;\n"
                    + "			min-height:1px;\n"
                    + "			display:block;\n"
                    + "			width:700px;\n"
                    + "			min-width:700px;\" width=\"700\" border=\"0\" height=\"1\">\n"
                    + "        </td>\n"
                    + "    </tr>\n"
                    + "    </tbody>\n"
                    + "    </table>\n"
                    + "</body>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
 @Override
    public List<demandeTravail> offresimilairaunedemande(int id) {
         List<demandeTravail> demandesTravail = new ArrayList<>();
      try {
            String req2 = "SELECT *  FROM demandeTravail2 INNER JOIN offreTravail2 ON offreTravail2.titreOffre= demandeTravail2.titreDemande where offreTravail2.ID_User= " + id+"";
           Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req2);
            
            while (rs.next()) {
                demandeTravail dm = new demandeTravail();
                dm.setIdDemande(rs.getInt(7));
                dm.setIdArtiste(rs.getInt(1));
                dm.setNomArtiste(rs.getString(2));
                dm.setTitreDemande(rs.getString(3));
                dm.setDescriptionDemande(rs.getString(4));
                 Category c = new Category(rs.getInt(8),rs.getString(5));
                dm.setCategorieDemande(c);
                dm.setDateAjoutDemande(rs.getTimestamp(6));
                demandesTravail.add(dm);
                dm.setPdf(rs.getString("pdf"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return demandesTravail;
    }
      @Override
    public List<demandeTravail> fetchDemandesPerCategorieDate( List<demandeTravail>d,Category c) {
        List<demandeTravail>demandeTravails = new ArrayList<>();
        return d.stream().filter(o -> o.getCategorieDemande().getName_category().equals(c.getName_category())).collect(Collectors.toList());
    }
}
