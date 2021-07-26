
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TIARA
 */
public class Operations {
    public static boolean isLogin(String username, String password, String usertype, JFrame frame) {
        try {
            Connection con = connectionHelper.getConnection();
            String mySqlQuery = "SELECT UID, Usertype, Nickname FROM login WHERE Username =  '"
                    + username + "' AND Password = '" 
                    + password + "' AND Usertype ='" 
                    + usertype + "'";
            PreparedStatement preparedStatement = con.prepareStatement(mySqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                LoginSession.UID = resultSet.getInt("UID");
                LoginSession.Usertype = resultSet.getString("UserType");
                LoginSession.Nickname = resultSet.getString("Nickname");
                
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Database Error : " + e.getMessage());
        }
        return false;
    }
}
