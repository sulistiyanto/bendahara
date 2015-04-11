/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author sulistiyanto
 */
public class PopUpMenu {

    public final ContextMenu contextMenu = new ContextMenu();

    public void contextMenuTextField(String message, TextField textField) {
        contextMenu.setAutoHide(false);
        contextMenu.getItems().clear();
        contextMenu.getItems().add(new MenuItem(message));
        contextMenu.show(textField, Side.BOTTOM, 10, 0);
    }

    public void focusedPropertyTextField(TextField textField) {
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (newPropertyValue) {
                contextMenu.hide();
            }
        });
    }

    public void contextMenuDate(String message, DatePicker datePicker) {
        contextMenu.setAutoHide(false);
        contextMenu.getItems().clear();
        contextMenu.getItems().add(new MenuItem(message));
        contextMenu.show(datePicker, Side.BOTTOM, 10, 0);
    }

    public void focusedPropertyDate(DatePicker datePicker) {
        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (newPropertyValue) {
                contextMenu.hide();
            }
        });
    }

    public void contextMenuTextArea(String message, TextArea textArea) {
        contextMenu.setAutoHide(false);
        contextMenu.getItems().clear();
        contextMenu.getItems().add(new MenuItem(message));
        contextMenu.show(textArea, Side.BOTTOM, 10, 0);
    }

    public void focusedPropertyTextArea(TextArea textArea) {
        textArea.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (newPropertyValue) {
                contextMenu.hide();
            }
        });
    }
    
    public void contextMenuComboBox(String message, ComboBox comboBox) {
        contextMenu.setAutoHide(false);
        contextMenu.getItems().clear();
        contextMenu.getItems().add(new MenuItem(message));
        contextMenu.show(comboBox, Side.BOTTOM, 10, 0);
    }

    public void focusedPropertyComboBox(ComboBox comboBox) {
        comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (newPropertyValue) {
                contextMenu.hide();
            }
        });
    }

}
