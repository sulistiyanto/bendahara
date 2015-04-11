/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.PopUpMenu;
import configure.SHA1Utility;
import configure.configScene;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import sql.interfaceAccount;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class AccountController extends interfaceAccount implements Initializable {

    @FXML
    private PasswordField txtPasswordOld;
    @FXML
    private TextField txtUsername, txtUsernameOld;
    @FXML
    private PasswordField txtPasswordNew;
    @FXML
    private PasswordField txtRePasswordNew;
    @FXML
    private HBox boxLoading;
    @FXML
    private Label labelLoadingStatus;
    @FXML
    private ProgressBar progressBarLoading;
    @FXML
    private Button btnSave, btnChange;

    PopUpMenu popUp = new PopUpMenu();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionUsername(ActionEvent event) {
        txtPasswordNew.requestFocus();
    }

    @FXML
    private void actionUsernameOld(ActionEvent event) {
        txtPasswordOld.requestFocus();
    }

    @FXML
    private void actionPasswordNew(ActionEvent event) {
        txtRePasswordNew.requestFocus();
    }

    @FXML
    private void actionRePasswordNew(ActionEvent event) {
        btnSave.requestFocus();
    }

    @FXML
    private void pressedSave(KeyEvent event) {
        save();
    }

    @FXML
    private void actionPassword(ActionEvent event) {
        btnChange.requestFocus();
    }

    @FXML
    private void actionChange(ActionEvent event) {
        if (txtUsernameOld.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi username lama anda!", txtUsernameOld);
        } else if (txtPasswordOld.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi password lama anda!", txtPasswordOld);
        } else {
            openNew();
        }
        popUp.focusedPropertyTextField(txtPasswordOld);
        popUp.focusedPropertyTextField(txtUsernameOld);
    }

    @FXML
    private void keyChange(KeyEvent event) {
        if (txtUsernameOld.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi username lama anda!", txtUsernameOld);
        } else if (txtPasswordOld.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi password lama anda!", txtPasswordOld);
        } else {
            openNew();
        }
        popUp.focusedPropertyTextField(txtPasswordOld);
        popUp.focusedPropertyTextField(txtUsernameOld);
    }

    @FXML
    private void actionSave(ActionEvent event) {
        save();
    }

    @FXML
    private void actionRefresh(ActionEvent event) {
        labelLoadingStatus.setText("Segarkan Data . . .");
        configScene.progressBarLoading(boxLoading, progressBarLoading);
        clear();
    }

    private void clear() {
        txtUsernameOld.setEditable(true);
        txtPasswordOld.setEditable(true);
        txtUsername.setEditable(false);
        txtPasswordNew.setEditable(false);
        txtRePasswordNew.setEditable(false);
        txtUsernameOld.setText("");
        txtPasswordOld.setText("");
        txtUsername.setText("");
        txtPasswordNew.setText("");
        txtRePasswordNew.setText("");
    }

    private void save() {
        if (txtUsername.isEditable() == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pesan Peringatan");
            alert.setHeaderText("Lihatlah pesan peringatan berikut!");
            alert.setContentText("Klik tombol Ganti!!");
            alert.showAndWait();
            txtPasswordOld.requestFocus();
        } else {
            if (txtUsername.getText().isEmpty()) {
                popUp.contextMenuTextField("Silahakan isi username", txtUsername);
            } else if (txtPasswordNew.getText().isEmpty()) {
                popUp.contextMenuTextField("Silahakan isi password", txtPasswordNew);
            } else if (txtRePasswordNew.getText().isEmpty()) {
                popUp.contextMenuTextField("Silahakan isi konfirmasi password", txtRePasswordNew);
            } else if (!txtRePasswordNew.getText().equals(txtPasswordNew.getText())) {
                popUp.contextMenuTextField("Password tidak cocok", txtRePasswordNew);
            } else {
                labelLoadingStatus.setText("Simpan Data . . .");
                configScene.progressBarLoading(boxLoading, progressBarLoading);
                update(txtUsername, txtPasswordNew);
                clear();
                txtUsernameOld.requestFocus();
            }
            popUp.focusedPropertyTextField(txtUsername);
            popUp.focusedPropertyTextField(txtPasswordNew);
            popUp.focusedPropertyTextField(txtRePasswordNew);
        }
    }

    private void openNew() {
        try {
            String password2 = "";
            String username = "";
            String user = txtUsernameOld.getText();
            String pass = SHA1Utility.getSHA1(txtPasswordOld.getText());
            connectionDB();
            String sql = "select username, password from account where id='1'";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                username = rs.getString(1);
                password2 = rs.getString(2);
            }
            if (pass.equals(password2) && user.equals(username)) {
                txtUsernameOld.setEditable(false);
                txtPasswordOld.setEditable(false);
                txtUsername.setEditable(true);
                txtPasswordNew.setEditable(true);
                txtRePasswordNew.setEditable(true);
                txtUsername.requestFocus();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Pesan Peringatan");
                alert.setHeaderText("Lihatlah pesan peringatan berikut!");
                alert.setContentText("Password anda tidak cocok!");
                alert.showAndWait();
            }
        } catch (UnsupportedEncodingException | SQLException e) {
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closed();
        }
    }
}
