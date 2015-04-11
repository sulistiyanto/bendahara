/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import com.aspose.pdf.jr5_6_0.jasperreports.JrPdfExporter;
import com.aspose.words.jasperreports.AWDocxExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author sulistiyanto
 */
public class Export {

    public void exportHTML(String reportName, JasperPrint jasperPrint) throws JRException {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportName);
        exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS, true);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
        exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
        exporter.exportReport();
    }

    public void exportWrod(String reportName, JasperPrint jasperPrint) throws JRException {
        AWDocxExporter export = new AWDocxExporter();
        export.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        export.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportName);
        export.exportReport();
    }

    public void exportExcel(String reportName, JasperPrint jasperPrint) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, reportName);
        exporter.exportReport();
    }

    public void exportPdf(String reportName, JasperPrint jasperPrint) throws JRException {
        JrPdfExporter ex = new JrPdfExporter();
        ex.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        ex.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportName);

    }
    
    public void chooseExtention(String tempPath, String reportName, JasperPrint jasperPrint) {
        try {
            if (tempPath.endsWith(".docx")) {
                exportWrod(reportName, jasperPrint);
            } else if (tempPath.endsWith(".xlsx")) {
                exportExcel(reportName, jasperPrint);
            } else if (tempPath.endsWith(".pdf")) {
                exportPdf(reportName, jasperPrint);
            } else if (tempPath.endsWith(".html")) {
               exportHTML(reportName, jasperPrint);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
