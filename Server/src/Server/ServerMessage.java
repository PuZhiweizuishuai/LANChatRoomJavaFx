package Server;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private static final long serialVersionUID = 0000000000L;
    public static int PORT = 9999;
    public static String MYSQLPORT = "3306";
    public static String MYSSQL = "192.168.244.129";
    public static String mysqlName = "root";
    public static String mysqlPassword = "123456";


    public ServerMessage() {

    }
}
