/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author sulistiyanto
 */
public class CreateDB {

    public CreateDB() {
    }

    public Connection create() {
        try {
            String databaseName = "bendahara";
            String userName = "root";
            String password = "";

            String url = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull";
            Connection connection = DriverManager.getConnection(url, userName, password);

            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
