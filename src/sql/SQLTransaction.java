/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import entities.Transaction;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 *
 * @author sulistiyanto
 */
public class SQLTransaction extends ConnectionDB {

    //insert Truncate table XXX
    protected void insert(Transaction transaction) {
        try {
            connectionDB();
            String sql = "insert into transaksi values ('" + transaction.getTanggal1() + "', '"
                    + transaction.getKeterangan() + "', '" + transaction.getDebit() + "', '"
                    + transaction.getKredit() + "', '" + transaction.getSaldo() + "', '"
                    + transaction.getKode() + "')";
            st.executeUpdate(sql);
            closed();
        } catch (Exception e) {
            System.out.println("tidak tersimpan" + e);
        } finally {
            closed();
        }
    }

    //Update
    protected void update(Transaction transaction) {
        try {
            connectionDB();
            String sql = "update transaksi set tanggal='" + transaction.getTanggal1() + "', keterangan='"
                    + transaction.getKeterangan() + "', debit='" + transaction.getDebit() 
                    + "', kredit='" + transaction.getKredit() + "', saldo='" 
                    + transaction.getSaldo() + "' where kode='" + transaction.getKode() + "'";
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }
    
    //list table
    protected ObservableList<Transaction> listTransaction(DatePicker dateListStart, DatePicker dateListEnd) {
        try {
            connectionDB();
            ObservableList<Transaction> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from transaksi where tanggal between '"
                    + java.sql.Date.valueOf(dateListStart.getValue())
                    + "' and '" + java.sql.Date.valueOf(dateListEnd.getValue()) + "' order by kode asc");
            while (rs.next()) {
                Transaction transaction = new Transaction();
                Date dateInString = rs.getDate(1);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String date = String.valueOf(format.format(dateInString));
                transaction.setTanggal2(date);
                transaction.setKode(rs.getInt(6));
                transaction.setKeterangan(rs.getString(2));
                int kredit = rs.getInt(3);
                int debit = rs.getInt(4);
                int saldo = rs.getInt(5);

                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                transaction.setDebit2(kursIndonesia.format(kredit));
                transaction.setKredit2(kursIndonesia.format(debit));
                transaction.setSaldo2(kursIndonesia.format(saldo));
                list.add(transaction);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("list : " + e);
            return null;
        } finally {
            closed();
        }
    }

    //list table Kredit
    protected ObservableList<Transaction> listTransactionKredit(DatePicker dateListStart, DatePicker dateListEnd) {
        try {
            connectionDB();
            ObservableList<Transaction> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from transaksi where tanggal between '"
                    + java.sql.Date.valueOf(dateListStart.getValue())
                    + "' and '" + java.sql.Date.valueOf(dateListEnd.getValue()) + "' and debit='0' order by kode asc");
            while (rs.next()) {
                Transaction transaction = new Transaction();
                Date dateInString = rs.getDate(1);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String date = String.valueOf(format.format(dateInString));
                transaction.setTanggal2(date);

                transaction.setKeterangan(rs.getString(2));
                int kredit = rs.getInt(3);
                int debit = rs.getInt(4);
                int saldo = rs.getInt(5);

                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                transaction.setDebit2(kursIndonesia.format(kredit));
                transaction.setKredit2(kursIndonesia.format(debit));
                transaction.setSaldo2(kursIndonesia.format(saldo));
                list.add(transaction);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("list : " + e);
            return null;
        } finally {
            closed();
        }
    }

    //list table Kredit
    protected ObservableList<Transaction> listTransactionDebit(DatePicker dateListStart, DatePicker dateListEnd) {
        try {
            connectionDB();
            ObservableList<Transaction> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from transaksi where tanggal between '"
                    + java.sql.Date.valueOf(dateListStart.getValue())
                    + "' and '" + java.sql.Date.valueOf(dateListEnd.getValue()) + "' and kredit='0' order by kode asc");
            while (rs.next()) {
                Transaction transaction = new Transaction();
                Date dateInString = rs.getDate(1);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String date = String.valueOf(format.format(dateInString));
                transaction.setTanggal2(date);

                transaction.setKeterangan(rs.getString(2));
                int kredit = rs.getInt(3);
                int debit = rs.getInt(4);
                int saldo = rs.getInt(5);

                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                transaction.setDebit2(kursIndonesia.format(kredit));
                transaction.setKredit2(kursIndonesia.format(debit));
                transaction.setSaldo2(kursIndonesia.format(saldo));
                list.add(transaction);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("list : " + e);
            return null;
        } finally {
            closed();
        }
    }

    //delete 
    protected void delete(int kode) {
        try {
            connectionDB();
            String sql = "delete from transaksi where kode='" + kode + "'";
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closed();
        }
    }
}
