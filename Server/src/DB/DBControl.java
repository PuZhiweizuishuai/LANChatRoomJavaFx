package DB;

import ChatMessage.user.Message;

import java.awt.*;
import java.sql.*;

/**
 * 数据库控制模块
 * @author Pu Zhiwei
 * */
public class DBControl {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.244.129:3306/ChatMessage";
    private static String user = "root";
    private static String password = "123456";
    private static Connection con;

    private static void connect() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 登陆验证
     * */
    public static boolean checkUserNameAndPwd(String sql, Message message) {
        connect();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String name = "", email = "", pwd = "";
            while (rs.next()) {
                name = rs.getString("Name");
                email = rs.getString("Email");
                pwd = rs.getString("Password");
            }
            if(name.equals("")) {
                System.out.println("用户名不存在");
                rs.close();
                con.close();
                return false;
            }
            if(name.equals(message.getName()) && pwd.equals(message.getPassword())) {
                System.out.println("登陆成功");
                rs.close();
                con.close();
                return true;
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 注册
     * */
    public static boolean signUp(Message message) {
        connect();
        try {
            String sql = "select * from User where Name = " + "'" + message.getName() + "'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String name = "", email = "", pwd = "";
            while (rs.next()) {
                name = rs.getString("Name");
                email = rs.getString("Email");
                pwd = rs.getString("Password");
            }
            System.out.println(name.equals(""));
            if(name.equals("")) {
                sql = "insert into User values (\"" +
                        message.getName() + "\",\"" + message.getEmail() + "\",\"" + message.getPassword() + "\")";
                PreparedStatement insert = con.prepareStatement(sql);
                insert.executeUpdate();
                con.close();
                return true;
            }
            con.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        //checkUserNameAndPwd();
    }


}
