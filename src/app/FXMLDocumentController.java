/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import configure.ClassStage;
import configure.PopUpMenu;
import configure.SHA1Utility;
import configure.configScene;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.ConnectionDB;

/**
 *
 * @author sulistiyanto
 */
public class FXMLDocumentController extends ClassStage implements Initializable {

    Stage primaryStage;
    PopUpMenu popUp = new PopUpMenu();
    ConnectionDB con = new ConnectionDB();

    @FXML
    private Button btnTransaction, btnSetting, btnPrint, btnLogin, btnLogout;
    @FXML
    private TextField txtUsername, txtPassword;

    @FXML
    private void actionTransaction(ActionEvent event) {
        stageChild(event, URL_TRANSACTION_FXML, URL_TRANSACTION_TITLE,
                URL_IMAGE, primaryStage);
    }

    @FXML
    private void actionSetting(ActionEvent event) {
        stageChild(event, URL_SETTING_FXML, URL_SETTING_TITLE, URL_IMAGE, primaryStage);
    }

    @FXML
    private void actionReport(ActionEvent event) {
        stageChild(event, URL_REPORT_FXML, URL_REPORT_TITLE, URL_IMAGE, primaryStage);
    }

    @FXML
    private void actionLogin(ActionEvent event) {
        if (txtUsername.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi username", txtUsername);
        } else if (txtPassword.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi password", txtPassword);
        } else {
            Login();
        }
        popUp.focusedPropertyTextField(txtUsername);
        popUp.focusedPropertyTextField(txtPassword);
    }

    @FXML
    private void keyLogin(KeyEvent event) {
        if (txtUsername.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi username", txtUsername);
        } else if (txtPassword.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi password", txtPassword);
        } else {
            Login();
        }
        popUp.focusedPropertyTextField(txtUsername);
        popUp.focusedPropertyTextField(txtPassword);
    }

    @FXML
    private void actionLogout(ActionEvent event) {
        btnTransaction.setDisable(true);
        btnPrint.setDisable(true);
        btnLogin.setVisible(true);
        btnLogout.setVisible(false);
        txtPassword.setEditable(true);
        txtUsername.setEditable(true);
    }

    @FXML
    private void actionUsername(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    private void actionPassword(ActionEvent event) {
        btnLogin.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con.connectionDB();
        con.closed();
    }

    private void Login() {
        try {
            String user = txtUsername.getText();
            String pass = SHA1Utility.getSHA1(txtPassword.getText());
            String u = "", p = "";
            con.connectionDB();
            con.rs = con.st.executeQuery("select * from account where username='" + user + "' and password='" + pass + "' ");
            while (con.rs.next()) {
                u = con.rs.getString(2);
                p = con.rs.getString(3);
            }
            con.rs.close();
            if (u.equals(user) && p.equals(pass)) {
                btnPrint.setDisable(false);
                btnTransaction.setDisable(false);
                btnLogin.setVisible(false);
                btnLogout.setVisible(true);
                txtUsername.setEditable(false);
                txtPassword.setEditable(false);
                txtPassword.setText("");
                txtUsername.setText("");
            } else {
                configScene.createDialog(Alert.AlertType.INFORMATION, "Maaf username dan password tidak cocok");
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException ex) {
            System.out.println(ex);
        } finally {
            con.closed();
        }
    }

}
