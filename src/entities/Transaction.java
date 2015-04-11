/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author sulistiyanto
 */
public class Transaction {
    
    private Date tanggal1;
    private String keterangan;
    private int debit;
    private int kredit;
    private int saldo;
    private String tanggal2;
    private String debit2;
    private String kredit2;
    private String saldo2;
    private int kode;

    public Date getTanggal1() {
        return tanggal1;
    }

    public void setTanggal1(Date tanggal1) {
        this.tanggal1 = tanggal1;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public int getKredit() {
        return kredit;
    }

    public void setKredit(int kredit) {
        this.kredit = kredit;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getTanggal2() {
        return tanggal2;
    }

    public void setTanggal2(String tanggal2) {
        this.tanggal2 = tanggal2;
    }

    public String getDebit2() {
        return debit2;
    }

    public void setDebit2(String debit2) {
        this.debit2 = debit2;
    }

    public String getKredit2() {
        return kredit2;
    }

    public void setKredit2(String kredit2) {
        this.kredit2 = kredit2;
    }

    public String getSaldo2() {
        return saldo2;
    }

    public void setSaldo2(String saldo2) {
        this.saldo2 = saldo2;
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

}
