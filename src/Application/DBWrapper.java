package Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBWrapper {
    public static ResultSet result;
    static PreparedStatement prpstmt;

    public static void select(String sql) {
        try {
            prpstmt = DatabaseConnector.getConnection().prepareStatement(sql);
            result = prpstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("select OK");
    }

    public static void update(String sql) {
        try {
            prpstmt = DatabaseConnector.getConnection().prepareStatement(sql);
            int result = prpstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("update OK, rows affected: " + result);
    }

    public static void insert(String sql) {
        try {
            prpstmt = DatabaseConnector.getConnection().prepareStatement(sql);
            int result = prpstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("insert OK");
    }

    public static void delete(String sql) {
        try {
            prpstmt = DatabaseConnector.getConnection().prepareStatement(sql);
            int result = prpstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("delete OK");
    }

    public static ResultSet getResultSet() {
        return result;
    }
}
