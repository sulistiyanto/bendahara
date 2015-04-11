/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.Code;
import configure.FormatDate;
import configure.PopUpMenu;
import configure.configScene;
import entities.Transaction;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sql.interfaceTransaction;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class TransactionController extends interfaceTransaction implements Initializable {

    FormatDate formatDate = new FormatDate();
    PopUpMenu popUp = new PopUpMenu();

    @FXML
    private RadioButton rbInputKredit;
    @FXML
    private RadioButton rbInputDebet;
    @FXML
    private RadioButton rbListDebet;
    @FXML
    private RadioButton rbListKredit;
    @FXML
    private RadioButton rbAll;
    @FXML
    private DatePicker dateInput;
    @FXML
    private DatePicker dateListStart;
    @FXML
    private DatePicker dateListEnd;
    @FXML
    private TextField txtTransaction;
    @FXML
    private TextArea txtInfo;
    @FXML
    private HBox boxLoading;
    @FXML
    private Label labelLoadingStatus, label, kode;
    @FXML
    private ProgressBar progressBarLoading;
    @FXML
    private TableView<Transaction> tableTransaction;
    @FXML
    private TableColumn<Transaction, String> colTanggal, colKeterangan, colDebit, colKredit, colSaldo;
    @FXML
    private TableColumn<Transaction, Integer> colKode;
    @FXML
    private TableColumn colAction;
    private final ObservableList<Transaction> listTransaction = FXCollections.observableArrayList();

    private Boolean statusSaveOrUpdate = false;
    private Integer statusAction, onKlik, onSelect;
    Code code = new Code();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbInputKredit.setSelected(true);
        rbAll.setSelected(true);
        formatDate.datePicker(dateInput);
        formatDate.datePicker(dateListEnd);
        formatDate.datePicker(dateListStart);
        dateInput.setValue(LocalDate.now());
        dateListEnd.setValue(LocalDate.now());
        monthAgo();
        code.noSerial(kode);
        listTableTransaction(colTanggal, colKeterangan, colDebit, colKredit, colSaldo, colKode);
        refreshTransaction(tableTransaction, listTransaction, dateListStart, dateListEnd);
        txtTransaction.setAlignment(Pos.CENTER_RIGHT);
        totalSaldo(label);
        txtTransaction.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                char ch = txtTransaction.getText().charAt(oldValue.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    txtTransaction.setText(txtTransaction.getText().substring(0, txtTransaction.getText().length() - 1));
                }
            }
        });
        colSaldo.setCellFactory(new Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                TableCell cell = new TableCell<Transaction, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };
                cell.setStyle("-fx-alignment: CENTER-RIGHT;");
                return cell;
            }
        });
        colKredit.setCellFactory(new Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                TableCell cell = new TableCell<Transaction, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };
                cell.setStyle("-fx-alignment: CENTER-RIGHT;");
                return cell;
            }
        });
        colDebit.setCellFactory(new Callback<TableColumn<Transaction, String>, TableCell<Transaction, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                TableCell cell = new TableCell<Transaction, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };
                cell.setStyle("-fx-alignment: CENTER-RIGHT;");
                return cell;
            }
        });
        classAction();
    }

    @FXML
    private void actionInputKredit(ActionEvent event) {
        rbInputDebet.setSelected(false);
        rbInputKredit.setSelected(true);
    }

    @FXML
    private void actionInputDebit(ActionEvent event) {
        rbInputDebet.setSelected(true);
        rbInputKredit.setSelected(false);
    }

    @FXML
    private void actionSave(ActionEvent event) {
        try {
            Date date = java.sql.Date.valueOf(dateInput.getValue());
            if (txtTransaction.getText().isEmpty()) {
                popUp.contextMenuTextField("Silahkan isi transaksi", txtTransaction);
            } else if (date == null) {
                popUp.contextMenuDate("Silahkan pilih tanggal", dateInput);
            } else if (txtInfo.getText().isEmpty()) {
                popUp.contextMenuTextArea("Silahkan isi Keterangan", txtInfo);
            } else {
                if (statusSaveOrUpdate == false) {
                    if (rbInputDebet.isSelected()) {
                        insertDebet(txtTransaction, dateInput, txtInfo, kode,
                                labelLoadingStatus, boxLoading, progressBarLoading);
                        refreshTransaction(tableTransaction, listTransaction, dateListStart, dateListEnd);
                        clear();
                    } else {
                        insertKredit(txtTransaction, dateInput, txtInfo, kode);
                        refreshTransaction(tableTransaction, listTransaction, dateListStart, dateListEnd);
                        labelLoadingStatus.setText("Simpan Data . . .");
                        configScene.progressBarLoading(boxLoading, progressBarLoading);
                        clear();
                    }
                } else {
                    if (rbInputDebet.isSelected()) {
                        updateDebet(txtTransaction, dateInput, txtInfo, kode,
                                labelLoadingStatus, boxLoading, progressBarLoading);
                        refreshTransaction(tableTransaction, listTransaction, dateListStart, dateListEnd);
                        clear();
                    } else {
                        updateKredit(txtTransaction, dateInput, txtInfo, kode);
                        refreshTransaction(tableTransaction, listTransaction, dateListStart, dateListEnd);
                        labelLoadingStatus.setText("Simpan Data . . .");
                        configScene.progressBarLoading(boxLoading, progressBarLoading);
                        clear();
                    }
                }
            }
            popUp.focusedPropertyTextField(txtTransaction);
            popUp.focusedPropertyDate(dateInput);
            popUp.focusedPropertyTextArea(txtInfo);
        } catch (Exception e) {
        }
    }

    @FXML
    private void actionRefresh(ActionEvent event) {
        labelLoadingStatus.setText("Segarkan Data . . .");
        configScene.progressBarLoading(boxLoading, progressBarLoading);
        clear();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 3);
    }

    @FXML
    private void actionListDebet(ActionEvent event) {
        rbAll.setSelected(false);
        rbListDebet.setSelected(true);
        rbListKredit.setSelected(false);
    }

    @FXML
    private void actionListKredit(ActionEvent event) {
        rbAll.setSelected(false);
        rbListDebet.setSelected(false);
        rbListKredit.setSelected(true);
    }

    @FXML
    private void actionAll(ActionEvent event) {
        rbAll.setSelected(true);
        rbListDebet.setSelected(false);
        rbListKredit.setSelected(false);
    }

    @FXML
    private void actionDisplay() {
        if (rbAll.isSelected()) {
            refreshTransaction(tableTransaction, listTransaction, dateListStart, dateListEnd);
            totalSaldo(label);
        } else if (rbListKredit.isSelected()) {
            refreshTransactionKredit(tableTransaction, listTransaction, dateListStart, dateListEnd);
            totalKredit(label, dateListStart, dateListEnd);
        } else if (rbListDebet.isSelected()) {
            refreshTransactionDebit(tableTransaction, listTransaction, dateListStart, dateListEnd);
            totalDebit(label, dateListStart, dateListEnd);
        }
    }

    @FXML
    private void clickedTableTransaction(MouseEvent event) {
        try {
            if (statusAction == 1) {
                configScene.onSelectTable(onKlik, onSelect, tableTransaction);
                try {
                    Transaction l = tableTransaction.getSelectionModel().getSelectedItem();
                    txtInfo.setText(l.getKeterangan());
                    kode.setText("" + l.getKode());
                    //convert string to localdate
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dateInString = l.getTanggal2();
                    Date date = formatter.parse(dateInString);
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                    dateInput.setValue(localDate);
                    if (l.getKredit2().equals("0,00")) {
                        rbInputDebet.setSelected(true);
                        rbInputKredit.setSelected(false);
                        String s1 = l.getDebit2();
                        String replaced = removeLastChar(s1).replaceAll(Pattern.quote("."), "");
                        txtTransaction.setText(replaced);
                    } else {
                        rbInputDebet.setSelected(false);
                        rbInputKredit.setSelected(true);
                        String s1 = l.getKredit2();
                        String replaced = removeLastChar(s1).replaceAll(Pattern.quote("."), "");
                        txtTransaction.setText(replaced);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void monthAgo() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar date = Calendar.getInstance();
            date.set(Calendar.DAY_OF_MONTH, 1);
            Date dat = sdf.parse(sdf.format(date.getTime()));
            Instant instant = Instant.ofEpochMilli(dat.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            dateListStart.setValue(localDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void clear() {
        txtTransaction.setText("");
        txtInfo.setText("");
        dateInput.setValue(LocalDate.now());
        dateListEnd.setValue(LocalDate.now());
        monthAgo();
        rbInputDebet.setSelected(false);
        rbInputKredit.setSelected(true);
        rbAll.setSelected(true);
        rbListDebet.setSelected(false);
        rbListKredit.setSelected(false);
        refreshTransaction(tableTransaction, listTransaction, dateListStart, dateListEnd);
        totalSaldo(label);
        code.noSerial(kode);
    }

    /*
     * fungsi tambahan
     */
    //implement button cell
    @SuppressWarnings("Convert2Lambda")
    private void classAction() {
        colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                return new ButtonCell(tableTransaction);
            }
        });
    }

    //button cell
    private class ButtonCell extends TableCell<Object, Boolean> {

        final Hyperlink cellButtonDelete = new Hyperlink("Hapus");
        final Hyperlink cellButtonEdit = new Hyperlink("Ubah");
        final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

        ButtonCell(final TableView tblView) {
            hb.setSpacing(4);
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Yakin Ingin Dihapus ?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        labelLoadingStatus.setText("Hapus Data . . .");
                        configScene.progressBarLoading(boxLoading, progressBarLoading);
                        statusAction = 1;
                        int row = getTableRow().getIndex();
                        tableTransaction.getSelectionModel().select(row);
                        clickedTableTransaction(null);
                        delete(tblView);
                        refreshTransaction(tblView, listTransaction, dateListStart, dateListEnd);
                        clear();
                        statusAction = 0;
                        onKlik = 0;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {

                }
            });

            cellButtonEdit.setOnAction((ActionEvent event) -> {
                try {
                    statusAction = 1;
                    int row = getTableRow().getIndex();
                    tableTransaction.getSelectionModel().select(row);
                    clickedTableTransaction(null);
                    statusAction = 0;
                    statusSaveOrUpdate = true;
                } catch (Exception e) {
                    System.out.println(e);
                }

            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(hb);
            } else {
                setGraphic(null);
            }
        }
    }
}
