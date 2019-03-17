package DB;
import Server.ServerMessage;

import java.sql.*;

public class DbConnect {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://" + ServerMessage.MYSSQL + ":" + ServerMessage.MYSQLPORT;
    private static String user = ServerMessage.mysqlName;
    private static String password = ServerMessage.mysqlPassword;
    private static Connection con;

    public static boolean connect() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
            }
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean foundChatMessage() {
        String mysqlurl = url + "/ChatMessage";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(mysqlurl,user,password);
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean fount() {
        connect();
        try {
            String sql = "CREATE DATABASE ChatMessag;";
            PreparedStatement insert = con.prepareStatement(sql);
            insert.executeUpdate();
            sql = "USE ChatMessage;";
            insert = con.prepareStatement(sql);
            insert.executeUpdate();
            sql = "CREATE TABLE User (\n" +
                    "    Name VARCHAR(50) NOT NULL,\n" +
                    "    Email VARCHAR(100) NOT NULL,\n" +
                    "    Password VARCHAR(100) NOT NULL,\n" +
                    "    PRIMARY KEY(Name)  \n" +
                    ");";

            insert = con.prepareStatement(sql);
            insert.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        fount();
    }
}
