module com.example.integrithioni {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javax.mail;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.swing;
    requires itextpdf;
    requires stripe.java;
    requires javafx.media;

    requires javafx.graphics;
    exports controller;
    opens com.example.integrithioni to javafx.fxml;
    exports com.example.integrithioni;
}