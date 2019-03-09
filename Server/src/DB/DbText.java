package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbText {
    public static void main(String[] args) {
        Connection con;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.244.129:3306/ChatMessage";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
            }
            Statement statement = con.createStatement();
            String sql = "select * from User";
            ResultSet rs = statement.executeQuery(sql);
            String name, email, pwd;
            while (rs.next()) {
                name = rs.getString("Name");
                email = rs.getString("Email");
                pwd = rs.getString("Password");
                System.out.println(name + "  " + email + "  " + pwd);
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
