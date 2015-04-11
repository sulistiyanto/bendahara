/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sulistiyanto
 */
public class ChangePass {

    public ChangePass() {
    }

    public Connection change() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "SET PASSWORD FOR 'root'@'localhost' = PASSWORD('scorpion')";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
