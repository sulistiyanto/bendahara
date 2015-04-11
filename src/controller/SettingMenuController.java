/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.configScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class SettingMenuController implements Initializable {
    
    configScene cs = new configScene();
    @FXML
    private Tab tabDatabase;
    @FXML
    private AnchorPane panelLoadDatabase;
    @FXML
    private Tab tabAccount;
    @FXML
    private AnchorPane panelAccount;
    @FXML
    private Tab tabAbout;
    @FXML
    private AnchorPane panelLoadAbout;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load pane Player Di tab 3
        cs.loadAnchorPane(panelLoadAbout, "/view/AboutFXML.fxml");
        //load pane Player Di tab 1
        cs.loadAnchorPane(panelLoadDatabase, "/view/Database.fxml");
        cs.loadAnchorPane(panelAccount, "/view/Account.fxml");
    }    
    
}
