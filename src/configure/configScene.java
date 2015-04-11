/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import animation.loadingStatusIn;
import animation.loadingStatusUp;

/**
 *
 * @author sulistiyanto
 */
public class configScene {

    public configScene() {
    }

    //dialog jfx
    public static void createDialog(Alert.AlertType alertType, String s) {
        Alert alert = new Alert(alertType, s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }

    //select table auto, saat save data
    public static void selectedTable(Integer click, Integer select, TableView tv) {
        if (click == 1) {
            tv.getSelectionModel().select(select);
            tv.scrollTo(select);
        } else {
            tv.getSelectionModel().selectLast();
            int table = tv.getSelectionModel().getSelectedIndex();
            tv.scrollTo(table);
        }
    }

    //pengaturan select saat di klik
    public static void onSelectTable(Integer click, Integer select, TableView tv) {
        click = 1;
        select = tv.getSelectionModel().getSelectedIndex();
    }

    //loading progress bar
    public static void progressBarLoading(HBox hb, ProgressBar pb) {
        new loadingStatusIn(hb).play();
        new loadingStatusUp(hb).play();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                for (int i = 1; i < 100; i++) {
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                    }
                    updateProgress(i + 1, 100);
                }
                return null;
            }
        };
        pb.progressProperty().bind(task.progressProperty());
        Thread th = new Thread(task);
        th.start();
    }

    //load Pane
    public void loadAnchorPane(AnchorPane ap, String a) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource(a));
            ap.getChildren().add(p);
        } catch (IOException e) {
        }
    }
}
