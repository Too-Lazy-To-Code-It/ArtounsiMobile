package service;

import models.AllUsers;
import models.Category;

import java.util.Properties;
import interfaces.offreTravailInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.artistepostuler;
import models.offreTravail;
import util.MaConnexion;
import java.util.Date;
import java.sql.Timestamp;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
//import org.apache.pdfbox.pdmodel.PDDocument;
public class offreTravailService implements offreTravailInterface {
    //var
    Connection cnx = MaConnexion.getInstance().getCnx();
    AllUsers studio = new  AllUsers();
    //***********************verifier si  les informations ajoutés d'offre de travail contient des gros mots*****************
    
    public Boolean grosMots(String words) {
        grosMotsService gros = new grosMotsService();
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
 public static boolean verifajout=false;
    @Override
    //********************ajouter d'un offre de travail**************************
   
    public void addOffre(offreTravail o) {
        AllUsers studio = new AllUsers();
        
        //ajouter l'utilisateur connecter pour recuperer ces informations qui doit etre ajouter par la suite dans la table offretravail
        try {
            String req2 = "SELECT * FROM `allusers` WHERE ID_User= \'" + o.getIdStudio() + "\' and Type= 'Studio'";
            Statement st1 = cnx.createStatement();
            ResultSet rs1 = st1.executeQuery(req2);
            while (rs1.next()) {
               
 studio.setID_User(rs1.getInt(1));
                studio.setName(rs1.getString(2));
                studio.setLast_Name(rs1.getString(3));
                studio.setEmail(rs1.getString(4));
                studio.setBirthday(rs1.getDate(5).toLocalDate());
                studio.setPassword(rs1.getString(6));
                studio.setNationality(rs1.getString(8));
                studio.setType(rs1.getString(9));
                studio.setNickname(rs1.getString(10));
studio.setDescription(rs1.getString(13));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // ensuite ajouter le date systeme************************
        Date date = new Date();
        Timestamp sqldate = new java.sql.Timestamp(date.getTime());

        
        try {
            //verifier si l'offre est deja ajouter par le studio conencter ou non en collecton les offre qui l  id studio et titre doffre
            String req3 = "SELECT * FROM `offretravail2` WHERE ID_User= \'" + o.getIdStudio() + "\'and titreOffre=\'" + o.getTitreOffre() + "\'";
            Statement st3 = cnx.createStatement();
            ResultSet rs3 = st3.executeQuery(req3);
            if (grosMots(o.getTitreOffre()) || grosMots(o.getDescriptionOffre())) {
            } else if (rs3.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("vous avez deja publier l' offre \'" + o.getTitreOffre() + "\' !");

                alert.showAndWait();
                System.out.println("vous avez deja publier l' offre \'" + o.getTitreOffre() + "\' !");
            } else {
                //verifier les gros mots*******************************
                if (o.getTitreOffre().equals("") || o.getDescriptionOffre().equals("") || o.getCategorieOffre().equals("") || o.getTypeOffre().equals("") || o.getLocalisationOffre().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("veuiller remplir tous les champs");
                    alert.showAndWait();
                } else
                {
                       //ajotuer l'offre de travail***************************
                    String req = "INSERT INTO `offreTravail2`(`ID_User`,`Nickname`,`titreOffre`, `descriptionOffre`, `categorieOffre`,`dateAjoutOffre`,`typeOffre`,`localisationOffre`,`idCategorie`) VALUES (?,?,?,?,?,?,?,?,?)";

                    PreparedStatement ps = cnx.prepareStatement(req);
                    ps.setInt(1, o.getIdStudio());
                    ps.setString(2, studio.getNickname());
                    ps.setString(3, o.getTitreOffre());
                    ps.setString(4, o.getDescriptionOffre());
                    ps.setString(5, o.getCategorieOffre().getName_category());
                    ps.setString(7, o.getTypeOffre());
                    ps.setString(8, o.getLocalisationOffre());
                    ps.setInt(9, o.getCategorieOffre().getId_Category());

                    ps.setTimestamp(6, sqldate);
                    ps.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("offre de travail ajouté");

                    alert.showAndWait();
                    System.out.println("offre de travail ajouté");
                    verifajout=true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }
//**********************************afficher tous les offres de travail*********************************

    @Override
    public List<offreTravail> fetchOffresPerDate() {
        List<offreTravail> offresTravail = new ArrayList<>();
        try {

            String req = "SELECT * FROM offreTravail2  ORDER BY dateAjoutOffre DESC";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                offreTravail of = new offreTravail();
                Category c = new Category();
                of.setIdOffre(rs.getInt("idOffre"));
                of.setIdStudio(rs.getInt(1));
                of.setNomStudio(rs.getString(5));
                of.setTitreOffre(rs.getString(2));
                of.setDescriptionOffre(rs.getString(3));

                c.setId_Category(rs.getInt("idCategorie"));
                c.setName_category(rs.getString("categorieOffre"));
                of.setCategorieOffre(c);
                of.setDateAjoutOffre(rs.getTimestamp(6));
                of.setTypeOffre(rs.getString(7));
                of.setLocalisationOffre(rs.getString(8));

                offresTravail.add(of);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return offresTravail;

    }

    @Override
    //***************************afficher les offres de travail d'utilisateur connecté*********************************
    public List<offreTravail> fetchOffresPerIdDate(int id) {
        List<offreTravail> offresTravail = new ArrayList<>();

        try {

            String req = "SELECT * FROM `offretravail2` WHERE ID_User= \'" + id + "\' ORDER BY dateAjoutOffre DESC";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                offreTravail of = new offreTravail();

                of.setIdOffre(rs.getInt(10));
                of.setIdStudio(rs.getInt(1));
                of.setNomStudio(rs.getString(5));
                of.setTitreOffre(rs.getString(2));
                of.setDescriptionOffre(rs.getString(3));
                Category c = new Category(rs.getInt(9), rs.getString(4));
                of.setCategorieOffre(c);
                of.setDateAjoutOffre(rs.getTimestamp(6));
                String categorieinfos = c.toString();
                of.setTypeOffre(rs.getString(7));
                of.setLocalisationOffre(rs.getString(8));

                offresTravail.add(of);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return offresTravail;

    }

    //****************************afficher par categorie**************************************
    @Override
    public List<offreTravail> fetchOffresPerCategorieDate(List<offreTravail> f, Category c) {
        List<offreTravail> offresTravail = new ArrayList<>();
        return f.stream().filter(o -> o.getCategorieOffre().getName_category().equals(c.getName_category())).collect(Collectors.toList());
    }

    //*******************filtre par type offre // autre methode bech yfiltri ali mawjoud deja******************************
    @Override
    public List<offreTravail> fetchOffresPerType(List<offreTravail> f, String type) {
        List<offreTravail> offresTravail = new ArrayList<>();
        return f.stream().filter(o -> o.getTypeOffre().equals(type)).collect(Collectors.toList());
    }
    //*******************filtre par localisation******************************

    @Override
    public List<offreTravail> fetchOffresPerLocalisation(List<offreTravail> f, String loc) {
        List<offreTravail> offresTravail = new ArrayList<>();
        return f.stream().filter(o -> o.getLocalisationOffre().equals(loc)).collect(Collectors.toList());
    }
//***************************afficher loffre par son id*****************************************

    public offreTravail fetchOffresParId(int id) {
        offreTravail of = new offreTravail();
        try {
            String req = "SELECT * FROM `offretravail2` WHERE idOffre= \'" + id + "\' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
           
            while (rs.next()) {
                
                Category c = new Category();
                of.setIdOffre(rs.getInt("idOffre"));
                of.setIdStudio(rs.getInt(1));
                of.setNomStudio(rs.getString(5));
                of.setTitreOffre(rs.getString(2));
                of.setDescriptionOffre(rs.getString(3));

                c.setId_Category(rs.getInt("idCategorie"));
                c.setName_category(rs.getString("categorieOffre"));
                of.setCategorieOffre(c);
                of.setDateAjoutOffre(rs.getTimestamp(6));
                of.setTypeOffre(rs.getString(7));
                of.setLocalisationOffre(rs.getString(8));

               
            }
        } catch (SQLException ex) {
            Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return of;
    }
//*************************modifier offre de travail********************************
public static boolean verif=false;
    @Override
    public void modifierOffre(offreTravail o) {
        try {
             //verifier si l'offre est deja ajouter par le studio conencter ou non en collecton les offre qui l  id studio et titre doffre
            String req3 = "SELECT COUNT(*)FROM `offretravail2` WHERE ID_User= \'" + o.getIdStudio() + "\'and titreOffre=\'" + o.getTitreOffre() + "\'";
            Statement st3 = cnx.createStatement();
            ResultSet rs3 = st3.executeQuery(req3);
            if (grosMots(o.getTitreOffre()) || grosMots(o.getDescriptionOffre())) {
            } else if (rs3.next() && rs3.getInt(1) > 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("vous avez deja publier l' offre \'" + o.getTitreOffre() + "\' veuillez changer le titre!");

                alert.showAndWait();
                
            } else {
  if (o.getTitreOffre().equals("") || o.getDescriptionOffre().equals("") || o.getCategorieOffre().equals("") || o.getTypeOffre().equals("") || o.getLocalisationOffre().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("veuiller remplir tous les champs");
                    alert.showAndWait();
                } else if ( o.getTitreOffre().trim().length() == 0|| o.getDescriptionOffre().trim().length() == 0)
                { Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Attention, il y'a un champs vide");
                    alert.showAndWait();}
  else
                {
            String req = "update `offretravail2` set titreOffre=?,descriptionOffre=?,categorieOffre=?,typeOffre=?,localisationOffre=?,idCategorie=? where idOffre=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, o.getTitreOffre());
            pst.setString(2, o.getDescriptionOffre());
            pst.setString(3, o.getCategorieOffre().getName_category());
            pst.setInt(7, o.getIdOffre());
            pst.setInt(6, o.getCategorieOffre().getId_Category());
            pst.setString(4, o.getTypeOffre());
            pst.setString(5, o.getLocalisationOffre());
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Offre de travail modifié  avec succès veuiller refrecher la page");

            alert.showAndWait();
             verif=true;
            System.out.println("Offre de travail modifié  avec succès"); }

            } } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //**********************deplacer offreer vers archive*******************************
    @Override
    public void Supprimeretajouterarchive(offreTravail o) {

        try {
              //  ajouter le date systeme*********************************
            Date date = new Date();
            Timestamp sqldate = new java.sql.Timestamp(date.getTime()); 
              //ajouter a larchive*******************************************************
            String req2 = "INSERT INTO `offreTravailArchive2`(`ID_User`,`Nickname`,`titreOffre`, `descriptionOffre`, `categorieOffre`,`dateAjoutOffre`,`typeOffre`,`localisationOffre`,`idCategorie`) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps;

            ps = cnx.prepareStatement(req2);
            ps.setInt(1, o.getIdStudio());
            ps.setString(2, o.getNomStudio());
            ps.setString(3, o.getTitreOffre());
            ps.setString(4, o.getDescriptionOffre());
            ps.setString(5, o.getCategorieOffre().getName_category());
            ps.setString(7, o.getTypeOffre());
            ps.setString(8, o.getLocalisationOffre());
            ps.setInt(9, o.getCategorieOffre().getId_Category());

            ps.setTimestamp(6, sqldate);
            ps.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("offre de travail ajouté a larchive");

            alert.showAndWait();
            System.out.println("offre de travail ajouté a larchive");
            //supprimer from table offreTravail********************************
            Statement st = cnx.createStatement();
            String req = "DELETE FROM `offretravail2` WHERE idOffre= " + o.getIdOffre() + "";
            st.executeUpdate(req);
            System.out.println("Offre de travail supprimer avec succès");
           
          
          
        } catch (SQLException ex) {
            Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //verifier si loffre existe deja dans la liste de resultat de recherche ou non pour eviter la redondance
    public boolean containsId(List<offreTravail> list, int id) {

    
                return list.stream().filter(o -> o.getIdOffre() == (id)).findFirst().isPresent();


    }

//chercher des offres de travail*************************************************
    @Override
    public List<offreTravail> chercherOffres(String mots) {
        List<offreTravail> offresTravailtrouver = new ArrayList<>();
        try {
            String[] words = mots.split(" ");
            String req;
            for (String motss : words) {
                req = "SELECT * FROM `offretravail2` WHERE  titreOffre like '%" + motss + "%' or Nickname like '%" + motss + "%' or descriptionOffre like '%" + motss + "%' or categorieOffre like '%" + motss + "%'";
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    offreTravail of = new offreTravail();

                    of.setIdOffre(rs.getInt(1));
                    of.setTitreOffre(rs.getString(2));
                    of.setDescriptionOffre(rs.getString(3));
                    of.setNomStudio(rs.getString(4));
                    
                    Category c = new Category();
                     c.setId_Category(rs.getInt("idCategorie"));
                c.setName_category(rs.getString("categorieOffre"));
                   of.setCategorieOffre(c);
                    of.setLocalisationOffre(rs.getString("localisationOffre"));
                    of.setTypeOffre(rs.getString("typeOffre"));
                    of.setDateAjoutOffre(rs.getTimestamp("dateAjoutOffre"));
             
                of.setNomStudio(rs.getString(5));
               
                    if (!containsId(offresTravailtrouver, of.getIdOffre())) {
                        offresTravailtrouver.add(of);
                    }

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return offresTravailtrouver;
    }

    //postuler a un offre de travail*************************************************************
    @Override
    public void postuleViaMail(int idArtiste, offreTravail of) {
        String mailreceiver = "";
        try {

            artistepostuler artpostuler = new artistepostuler();
            System.out.println("preparing");
             AllUsers artiste = new  AllUsers();
            //recuperer le nom du studio proprietaire de loffre********************************
            String req1 = "SELECT Nickname  FROM `offreTravail2` WHERE idOffre= \'" + of.getIdOffre() + "\' ";
            Statement st5 = cnx.createStatement();
            ResultSet rs5 = st5.executeQuery(req1);

            while (rs5.next()) {
                of.setNomStudio(rs5.getString(1));

            }
        //recuperer le mail du studio proprietaire de nickname*******************************************************************
            String req6 = "SELECT Email FROM `allusers` WHERE Nickname= \'" + of.getNomStudio() + "\' ";
            Statement st6 = cnx.createStatement();
            ResultSet rs6 = st6.executeQuery(req6);

            while (rs6.next()) {
                mailreceiver = rs6.getString("Email");

            }
            //ajouter l'utilisateur connecter li howa lartiste li bch ypostuli***************************************************
            try {

                String req2 = "SELECT * FROM `allusers` WHERE ID_User= \'" + idArtiste + "\' and Type= 'Artist'";
                Statement st1 = cnx.createStatement();
                ResultSet rs1 = st1.executeQuery(req2);
                while (rs1.next()) {
                     artiste.setID_User(rs1.getInt(1));
                  artiste.setName(rs1.getString(2));
                 artiste.setLast_Name(rs1.getString(3));
                 artiste.setEmail(rs1.getString(4));
                 artiste.setBirthday(rs1.getDate(5).toLocalDate());
                 artiste.setPassword(rs1.getString(6));
                  artiste.setNationality(rs1.getString(8));
                 artiste.setType(rs1.getString(9));
                  artiste.setNickname(rs1.getString(10));
artiste.setDescription(rs1.getString(13));
                   
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                //verifier si l'artiste a deja postuller pour loffre donnée****************************************************
                String req3 = "SELECT * FROM `artistepostuler2` WHERE ID_User = \'" + idArtiste + "\' and idOffre =\'" + of.getIdOffre() + "\'";
                Statement st3 = cnx.createStatement();
                ResultSet rs3 = st3.executeQuery(req3);
                if (rs3.next()) {
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("erreur");
alert.setHeaderText(null);
alert.setContentText("vous avez deja postuler pour cette offre!");

alert.show();
                    System.out.println("vous avez deja postuler pour cette offre!");
                } //***********************************************
                else {

                    //initialiser le mail******************************************
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
                    //envoyer le mail*****************************************************************************
                    Message message = prepareMessage(session, myAccountEmail, mailreceiver, of, artiste.getName(), artiste.getDescription(), artiste.getEmail());
                    Transport.send(message);
                    //ajouter a la table artistepostuler*******************************************
                    //user************************************************************
                    AllUsers u = new  AllUsers();
                    u.setID_User(idArtiste);
                    u.setNickname(artiste.getNickname());
                    artpostuler.setIdArtiste(u);
                    //offre**********************************************************
                    offreTravail o = new offreTravail();
                    o.setIdOffre(of.getIdOffre());
                    o.setTitreOffre(of.getTitreOffre());

                    artpostuler.setIdOffre(o);
                    Date date = new Date();
                    Timestamp sqldate = new java.sql.Timestamp(date.getTime());
                    String req = "INSERT INTO `artistepostuler2`(`ID_User`,`idOffre`,nomArtiste,titreOffre,datepostuler) VALUES (?,?,?,?,?)";
                    PreparedStatement ps = cnx.prepareStatement(req);

                    ps.setInt(1, artpostuler.getIdArtiste().getID_User());
                    ps.setInt(2, artpostuler.getIdOffre().getIdOffre());
                    ps.setString(3, artpostuler.getIdArtiste().getNickname());
                    ps.setString(4, artpostuler.getIdOffre().getTitreOffre());
                   ps.setTimestamp(5, sqldate);
                    ps.executeUpdate();
  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("succés");
alert.setHeaderText(null);
alert.setContentText("votre demande est envoyé avec succes");

alert.show();
                    System.out.println("votre demande est envoyé avec succes");

                }
            } catch (SQLException ex) {
                Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//preparation du msg
    private static Message prepareMessage(Session session, String myAccountEmail, String mailreceiver, offreTravail of, String nomartiste, String decriptionartiste, String mail) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailreceiver));
            message.setSubject("1vouveau Candidature pour le poste de " + of.getTitreOffre() + " ");
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
                    + "                       Bonjour " + of.getNomStudio() + "!\n"
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
                    + "                         Le candidature  "  + nomartiste +  "  \n  decriver par :" + decriptionartiste + " \n   avec le mail: " + mail + " \n"
                    + " `\n  a postuler au poste  (" + of.getTitreOffre() + ") "
                    + "                    </h2>\n"
                    + "                    <p class=\"data\" style=\"text-align: justify-all;\n"
                    + "							align-items: center;\n"
                    + "							font-size: 15px;\n"
                    + "							padding-bottom: 12px;color:white\">\n"
                    + "                       Description de l'offre:  " + of.getDescriptionOffre() + "\n"
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
    //***********afficher les personnes qui ont postuler pour les offres publier par le studio connecté***************
    @Override
    public List<String> affichernotifications(int idStudio) {
        List<String> artistespostuler = new ArrayList<>();
        try {
            List<offreTravail> of = fetchOffresPerIdDate(idStudio);
            //recuperer tous les offre de studio connecter
            int[] tab = of.stream().mapToInt(offreTravail::getIdOffre).toArray();
            for (int i = 0; i < tab.length; i++) {
                String req3 = "SELECT * FROM `artistepostuler2` WHERE idOffre = " + tab[i] + " ";
                Statement st3 = cnx.createStatement();
                ResultSet rs3 = st3.executeQuery(req3);
                while (rs3.next()) {
                    
                    String result = "l'artiste " + rs3.getString("ID_User") + "a postuler a l'offre" + rs3.getString("titreOffre") + "";
                    artistespostuler.add(result);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(offreTravailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return artistespostuler;
    }
    @Override
    public List<offreTravail> offresimilairaunedemande(int id) {
         List<offreTravail> offresTravail = new ArrayList<>();
      try {
         
            String req2 = "SELECT *  FROM offreTravail2 INNER JOIN demandeTravail2 ON offreTravail2.titreOffre= demandeTravail2.titreDemande where demandeTravail2.ID_User= " + id+"";
           Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req2);
            while (rs.next()) {
                offreTravail of = new offreTravail();
                Category c = new Category();
                of.setIdOffre(rs.getInt("idOffre"));
                of.setIdStudio(rs.getInt(1));
                of.setNomStudio(rs.getString(5));
                of.setTitreOffre(rs.getString(2));
                of.setDescriptionOffre(rs.getString(3));

                c.setId_Category(rs.getInt("idCategorie"));
                c.setName_category(rs.getString("categorieOffre"));
                of.setCategorieOffre(c);
                of.setDateAjoutOffre(rs.getTimestamp(6));
                of.setTypeOffre(rs.getString(7));
                of.setLocalisationOffre(rs.getString(8));

                offresTravail.add(of);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return offresTravail;
    }
}
