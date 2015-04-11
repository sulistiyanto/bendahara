/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author sulistiyanto
 */
public class ConnectionDB {

    public Connection con;
    public Statement st;
    public ResultSet rs;
    private final String driver = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost/bendahara";
    private final String username = "root";
    private final String password = "scorpion";

    public ConnectionDB() {
    }

    public Connection connectionDB() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Pesan Peringatan");
            alert.setHeaderText("Perhatikan Pesan Berikut!");
            alert.setContentText("Koneksi database gagal!\nSilahkan jalankan dulu MYSQL di XAMPP!");
            alert.showAndWait();
            System.exit(0);
        } 
        return con;
    }

    public void closed() {
        try {
            con.close();
            st.close();
        } catch (Exception e) {
            System.out.println("close failed" + e);
        }
    }
}
