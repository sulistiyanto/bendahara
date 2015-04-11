/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sql.ChangePass;
import sql.CreateDB;

/**
 *
 * @author sulistiyanto
 */
public class Bendahara extends Application {

    CreateDB c = new CreateDB();
    ChangePass p = new ChangePass();
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/app/FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        c.create();
        p.change();
        stage.setScene(scene);
        Image image = new Image("/image/ic_launcher.png");
        stage.getIcons().addAll(image);
        stage.setTitle("Aplikasi Bendahara");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
