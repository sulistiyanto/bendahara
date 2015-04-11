/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.Export;
import configure.FormatDate;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sql.ConnectionDB;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class ReportController extends ConnectionDB implements Initializable {

    FormatDate formatDate = new FormatDate();
    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;
    Map<String, Object> param = new HashMap<>();
    Export export = new Export();

    @FXML
    private RadioButton rbAll;
    @FXML
    private RadioButton rbKredit;
    @FXML
    private RadioButton rbDebit;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbAll.setSelected(true);
        formatDate.datePicker(dateStart);
        formatDate.datePicker(dateEnd);
        dateEnd.setValue(LocalDate.now());
        monthAgo();
    }

    @FXML
    private void actionAll(ActionEvent event) {
        rbAll.setSelected(true);
        rbDebit.setSelected(false);
        rbKredit.setSelected(false);
    }

    @FXML
    private void actionKredit(ActionEvent event) {
        rbAll.setSelected(false);
        rbDebit.setSelected(false);
        rbKredit.setSelected(true);
    }

    @FXML
    private void actionDebit(ActionEvent event) {
        rbAll.setSelected(false);
        rbDebit.setSelected(true);
        rbKredit.setSelected(false);
    }

    @FXML
    private void actionExport(ActionEvent event) {
        try {
            if (rbAll.isSelected()) {
                handleExport();
            } else if (rbDebit.isSelected()) {
                handleExportDebit();
            } else if (rbKredit.isSelected()) {
                handleExportKredit();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void actionPrint(ActionEvent event) {
        try {
            if (rbAll.isSelected()) {
                jasperRep();
                JasperViewer.viewReport(jasperPrint, false);
            } else if (rbDebit.isSelected()) {
                jasperRepDebit();
                JasperViewer.viewReport(jasperPrint, false);
            } else if (rbKredit.isSelected()) {
                jasperRepKredit();
                JasperViewer.viewReport(jasperPrint, false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            dateStart.setValue(localDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void jasperRep() {
        try {
            File file1 = new File("C:\\xampp\\htdocs\\bendahara\\All.jrxml");
            jasperDesign = JRXmlLoader.load(file1);
            String sql = "select * from transaksi where tanggal between '"
                    + java.sql.Date.valueOf(dateStart.getValue())
                    + "' and '" + java.sql.Date.valueOf(dateEnd.getValue()) + "' order by kode asc";
            JRDesignQuery newDesignQuery = new JRDesignQuery();
            newDesignQuery.setText(sql);
            jasperDesign.setQuery(newDesignQuery);
            param.clear();
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, connectionDB());
        } catch (Exception e) {
        }
    }

    private void jasperRepKredit() {
        try {
            File file1 = new File("C:\\xampp\\htdocs\\bendahara\\Kredit.jrxml");
            jasperDesign = JRXmlLoader.load(file1);
            String sql = "SELECT bendahara.transaksi.tanggal, bendahara.transaksi.keterangan, "
                    + "bendahara.transaksi.kredit, bendahara.transaksi.saldo "
                    + "FROM bendahara.transaksi where bendahara.transaksi.kredit >1 and tanggal between '"
                    + java.sql.Date.valueOf(dateStart.getValue())
                    + "' and '" + java.sql.Date.valueOf(dateEnd.getValue()) + "' order by kode asc";
            JRDesignQuery newDesignQuery = new JRDesignQuery();
            newDesignQuery.setText(sql);
            jasperDesign.setQuery(newDesignQuery);
            param.clear();
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, connectionDB());
        } catch (Exception e) {
        }
    }

    private void jasperRepDebit() {
        try {
            File file1 = new File("C:\\xampp\\htdocs\\bendahara\\Debit.jrxml");
            jasperDesign = JRXmlLoader.load(file1);
            String sql = "SELECT bendahara.transaksi.tanggal, bendahara.transaksi.keterangan, "
                    + "bendahara.transaksi.Debit, bendahara.transaksi.saldo "
                    + "FROM bendahara.transaksi where bendahara.transaksi.Debit >1 and tanggal between '"
                    + java.sql.Date.valueOf(dateStart.getValue())
                    + "' and '" + java.sql.Date.valueOf(dateEnd.getValue()) + "' order by kode asc";
            JRDesignQuery newDesignQuery = new JRDesignQuery();
            newDesignQuery.setText(sql);
            jasperDesign.setQuery(newDesignQuery);
            param.clear();
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, connectionDB());
        } catch (Exception e) {
        }
    }

    //export data
    @SuppressWarnings("null")
    private void handleExport() {
        try {
            jasperRep();
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterDocx = new FileChooser.ExtensionFilter("Word Document (*.docx)", "*.docx");
            FileChooser.ExtensionFilter extFilterXlsx = new FileChooser.ExtensionFilter("Excel Worlkbook (*.xlsx)", "*.xlsx");
            //FileChooser.ExtensionFilter extFilterPdf = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
            FileChooser.ExtensionFilter extFilterHtml = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
            fileChooser.getExtensionFilters().addAll(extFilterDocx, extFilterXlsx, extFilterHtml);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            //Show open file dialog
            File file = fileChooser.showSaveDialog(stage);
            String locationImage = file.getParent();
            String nameImage = file.getName();
            String reportName = locationImage + File.separator + nameImage;
            String tempPath = file.getCanonicalPath().toLowerCase();
            if (file != null) {
                if (file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else if (!file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else {

                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //export data
    @SuppressWarnings("null")
    private void handleExportKredit() {
        try {
            jasperRepKredit();
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterDocx = new FileChooser.ExtensionFilter("Word Document (*.docx)", "*.docx");
            FileChooser.ExtensionFilter extFilterXlsx = new FileChooser.ExtensionFilter("Excel Worlkbook (*.xlsx)", "*.xlsx");
            //FileChooser.ExtensionFilter extFilterPdf = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
            FileChooser.ExtensionFilter extFilterHtml = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
            fileChooser.getExtensionFilters().addAll(extFilterDocx, extFilterXlsx, extFilterHtml);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            //Show open file dialog
            File file = fileChooser.showSaveDialog(stage);
            String locationImage = file.getParent();
            String nameImage = file.getName();
            String reportName = locationImage + File.separator + nameImage;
            String tempPath = file.getCanonicalPath().toLowerCase();
            if (file != null) {
                if (file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else if (!file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else {

                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //export data
    @SuppressWarnings("null")
    private void handleExportDebit() {
        try {
            jasperRepDebit();
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterDocx = new FileChooser.ExtensionFilter("Word Document (*.docx)", "*.docx");
            FileChooser.ExtensionFilter extFilterXlsx = new FileChooser.ExtensionFilter("Excel Worlkbook (*.xlsx)", "*.xlsx");
            //FileChooser.ExtensionFilter extFilterPdf = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
            FileChooser.ExtensionFilter extFilterHtml = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
            fileChooser.getExtensionFilters().addAll(extFilterDocx, extFilterXlsx, extFilterHtml);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            //Show open file dialog
            File file = fileChooser.showSaveDialog(stage);
            String locationImage = file.getParent();
            String nameImage = file.getName();
            String reportName = locationImage + File.separator + nameImage;
            String tempPath = file.getCanonicalPath().toLowerCase();
            if (file != null) {
                if (file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else if (!file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else {

                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
