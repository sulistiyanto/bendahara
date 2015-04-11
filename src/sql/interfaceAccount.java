/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import configure.SHA1Utility;
import entities.Account;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author sulistiyanto
 */
public class interfaceAccount extends SQLAccount {

    Account account = new Account();

    protected void update(TextField txtUsername, PasswordField txtPasswordNew) {
        try {
            account.setUsername(txtUsername.getText());
            String crypto = SHA1Utility.getSHA1(txtPasswordNew.getText().trim());
            account.setPassword(crypto);
            account.setId(1);
            update(account);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }
}
