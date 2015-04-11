/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import java.sql.SQLException;
import javafx.scene.control.Label;
import sql.ConnectionDB;

/**
 *
 * @author sulistiyanto
 */
public class Code extends ConnectionDB{
    
    public void noSerial(Label kode) {
        String sql = "select kode from transaksi order by kode desc";
        try {
            connectionDB();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                int code = rs.getInt("kode") + 1;
                kode.setText(""+code);
            } else {
                int code = 1;
                kode.setText(""+code);
            }
            rs.close();
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
