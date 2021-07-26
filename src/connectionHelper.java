
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TIARA
 */

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author TIARA
 */
// untuk mendeklarasikan class
public class connectionHelper {
    private static final String DB_NAME = "sipenjualantoko";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost/" + DB_NAME;
    
    // membuat method connection
    public static Connection getConnection() throws  SQLException{
        
        //  untuk memanggil class dan untuk mengelola drive
        DriverManager.registerDriver(new Driver());
        
        // untuk membuat koneksi agar dapat terhubung dengan database 
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        
        // untuk mengembalikan isi dari method connection
        return connection;
    }
}
   

