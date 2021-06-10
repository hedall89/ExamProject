package Application;

import Domain.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector {

    static Connection con;

    public static java.sql.Connection createConnection() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=postit", "sa", "123456");
            System.out.println("Connected");
        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String) null, e);
        }
        return con;
    }

    public static Connection getConnection() {
        return con;
    }

    public static void manualDisconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}