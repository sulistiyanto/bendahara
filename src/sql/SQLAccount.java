/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import entities.Account;

/**
 *
 * @author sulistiyanto
 */
public class SQLAccount extends ConnectionDB{
    
    protected void update(Account account){
        try {
            connectionDB();
            String sql = "update account set username='" + account.getUsername() + "', password='"
                    + account.getPassword() + "' where id='" + account.getId() + "'";
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closed();
        }
    }
}
