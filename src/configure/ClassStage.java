/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sulistiyanto
 */
public class ClassStage {

    public final String URL_MAIN_FXML = "/view/GuestBook.fxml";
    public final String URL_TRANSACTION_FXML = "/view/Transaksi.fxml";
    public final String URL_REPORT_FXML = "/view/Report.fxml";
    public final String URL_SETTING_FXML = "/view/SettingMenu.fxml";

    public final String URL_MAIN_TITLE = "Menu";
    public final String URL_TRANSACTION_TITLE = "Transaksi Keuangan";
    public final String URL_REPORT_TITLE = "Laporan";
    public final String URL_SETTING_TITLE = "Pengaturan";

    public final String URL_IMAGE = "/image/ic_launcher.png";
    
    public void stageChild(ActionEvent event, String view, String title, String icon, Stage primaryStage) {
        try {
            URL url = getClass().getResource(view);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent) fxmlLoader.load(url.openStream());
            Stage stage = new Stage();
            Image image = new Image(icon);
            stage.setResizable(false);
            stage.getIcons().addAll(image);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void stageMouse(MouseEvent event, String view, String title, String icon, Stage primaryStage) {
        try {
            URL url = getClass().getResource(view);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent) fxmlLoader.load(url.openStream());
            Stage stage = new Stage();
            Image image = new Image(icon);
            stage.setResizable(false);
            stage.getIcons().addAll(image);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
