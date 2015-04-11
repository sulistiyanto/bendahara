/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import configure.configScene;
import entities.Transaction;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 *
 * @author sulistiyanto
 */
public class interfaceTransaction extends SQLTransaction {

    Transaction transaction = new Transaction();

    //insert data
    public void insertKredit(TextField txtTransaction, DatePicker dateInput, TextArea txtInformation, Label kode) {
        try {
            int saldoAwal = 0;
            int kredit = Integer.parseInt(txtTransaction.getText());
            transaction.setTanggal1(java.sql.Date.valueOf(dateInput.getValue()));
            transaction.setKredit(kredit);
            transaction.setDebit(0);
            connectionDB();
            String sql = "select saldo from transaksi order by kode desc limit 1";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                saldoAwal = rs.getInt("saldo");
            }
            int saldo = saldoAwal + kredit;
            transaction.setSaldo(saldo);
            transaction.setKeterangan(txtInformation.getText());
            transaction.setKode(Integer.parseInt(kode.getText()));
            insert(transaction);
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closed();
        }
    }

    //insert data
    public void insertDebet(TextField txtTransaction, DatePicker dateInput, TextArea txtInformation, Label kode, 
            Label labelLoadingStatus, HBox boxLoading, ProgressBar progressBarLoading) {
        try {
            int saldoAwal = 0;
            int debit = Integer.parseInt(txtTransaction.getText());
            transaction.setTanggal1(java.sql.Date.valueOf(dateInput.getValue()));
            transaction.setDebit(debit);
            transaction.setKredit(0);
            transaction.setKeterangan(txtInformation.getText());
            transaction.setKode(Integer.parseInt(kode.getText()));
            connectionDB();
            String sql = "select saldo from transaksi order by kode limit 1";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                saldoAwal = rs.getInt("saldo");
            }
            int saldo = saldoAwal - debit;
            if (saldo < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Look, an Warning Dialog");
                alert.setContentText("Maaf Saldo tidak mencukupi!");
                alert.showAndWait();
            } else {
                transaction.setSaldo(saldo);
                insert(transaction);
                labelLoadingStatus.setText("Simpan Data . . .");
                configScene.progressBarLoading(boxLoading, progressBarLoading);
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closed();
        }
    }

    //update data
    public void updateKredit(TextField txtTransaction, DatePicker dateInput, TextArea txtInformation, Label kode) {
        try {
            int saldoAwal = 0;
            int kredit = Integer.parseInt(txtTransaction.getText());
            transaction.setTanggal1(java.sql.Date.valueOf(dateInput.getValue()));
            transaction.setKredit(kredit);
            transaction.setDebit(0);
            connectionDB();
            String sql = "select saldo from transaksi order by kode desc limit 1";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                saldoAwal = rs.getInt("saldo");
            }
            int saldo = saldoAwal + kredit;
            transaction.setSaldo(saldo);
            transaction.setKeterangan(txtInformation.getText());
            transaction.setKode(Integer.parseInt(kode.getText()));
            update(transaction);
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closed();
        }
    }
    
    //update data
    public void updateDebet(TextField txtTransaction, DatePicker dateInput, TextArea txtInformation, Label kode, 
            Label labelLoadingStatus, HBox boxLoading, ProgressBar progressBarLoading) {
        try {
            int saldoAwal = 0;
            int debit = Integer.parseInt(txtTransaction.getText());
            transaction.setTanggal1(java.sql.Date.valueOf(dateInput.getValue()));
            transaction.setDebit(debit);
            transaction.setKredit(0);
            transaction.setKeterangan(txtInformation.getText());
            transaction.setKode(Integer.parseInt(kode.getText()));
            connectionDB();
            String sql = "select saldo from transaksi order by kode limit 1";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                saldoAwal = rs.getInt("saldo");
            }
            int saldo = saldoAwal - debit;
            if (saldo < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Look, an Warning Dialog");
                alert.setContentText("Maaf Saldo tidak mencukupi!");
                alert.showAndWait();
            } else {
                transaction.setSaldo(saldo);
                update(transaction);
                labelLoadingStatus.setText("Simpan Data . . .");
                configScene.progressBarLoading(boxLoading, progressBarLoading);
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closed();
        }
    }
    //list table
    public void listTableTransaction(TableColumn colTanggal, TableColumn colKeterangan, TableColumn colDebit,
            TableColumn colKredit, TableColumn colSaldo, TableColumn colKode) {
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal2"));
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        colDebit.setCellValueFactory(new PropertyValueFactory<>("debit2"));
        colKredit.setCellValueFactory(new PropertyValueFactory<>("kredit2"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo2"));
        colKode.setCellValueFactory(new PropertyValueFactory<>("kode"));
    }

    public void refreshTransaction(TableView tableTransaction, ObservableList listTransaction, DatePicker dateListStart, DatePicker dateListEnd) {
        listTransaction = listTransaction(dateListStart, dateListEnd);
        tableTransaction.setItems(listTransaction);
    }

    public void refreshTransactionKredit(TableView tableTransaction, ObservableList listTransaction, DatePicker dateListStart, DatePicker dateListEnd) {
        listTransaction = listTransactionKredit(dateListStart, dateListEnd);
        tableTransaction.setItems(listTransaction);
    }

    public void refreshTransactionDebit(TableView tableTransaction, ObservableList listTransaction, DatePicker dateListStart, DatePicker dateListEnd) {
        listTransaction = listTransactionDebit(dateListStart, dateListEnd);
        tableTransaction.setItems(listTransaction);
    }

    //saldo last
    public void totalSaldo(Label label) {
        try {
            int saldoAwal = 0;
            connectionDB();
            String sql = "select saldo from transaksi order by kode desc limit 1";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                saldoAwal = rs.getInt("saldo");
            }
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            label.setText("Saldo Akhir : Rp. " + kursIndonesia.format(saldoAwal));
        } catch (Exception e) {
        } finally {
            closed();
        }
    }

    //saldo last
    public void totalKredit(Label label, DatePicker dateListStart, DatePicker dateListEnd) {
        try {
            int kredit = 0;
            connectionDB();
            String sql = "select sum(kredit) from transaksi where tanggal between '2015-03-08' and '2015-04-08'";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                kredit = rs.getInt(1);
            }
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            label.setText("Total Pemasukan : Rp. " + kursIndonesia.format(kredit));
        } catch (Exception e) {
        } finally {
            closed();
        }
    }

    //saldo last
    public void totalDebit(Label label, DatePicker dateListStart, DatePicker dateListEnd) {
        try {
            int kredit = 0;
            connectionDB();
            String sql = "select sum(debit) from transaksi where tanggal between '2015-03-08' and '2015-04-08'";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                kredit = rs.getInt(1);
            }
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            label.setText("Total Pengeluaran : Rp. " + kursIndonesia.format(kredit));
        } catch (Exception e) {
        } finally {
            closed();
        }
    }

    //delete data
    public void delete(TableView tableTransaction) {
        try {
            Transaction l = (Transaction) tableTransaction.getSelectionModel().getSelectedItem();
            delete(l.getKode());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
