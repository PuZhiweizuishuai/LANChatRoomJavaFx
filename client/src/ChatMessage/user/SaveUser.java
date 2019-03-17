package ChatMessage.user;

import java.io.*;


public class SaveUser {
    private static String userName = "";
    private static String userPassword = "";
    private static String picPath = "/resources/images/defaultHead.png";
    /**
     * 对象序列化，保存用户信息
     * */
    public static void userSerialize(UserInformation user) throws Exception {
        File file = new File("src//Data//UserInformation.dat");
        OutputStream out = new FileOutputStream(file);
        ObjectOutputStream obj = new ObjectOutputStream(out);
        obj.writeObject(user);
        obj.close();
    }

    /**
     * 对象反序列化
     * */
    public static UserInformation userDeserialize() throws Exception {
        File file = new File("src//Data//UserInformation.dat");
        InputStream in = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(in);
        UserInformation c = new UserInformation("","","");
        while (in.available() > 0) {
            c = (UserInformation)ois.readObject();
        }
        ois.close();
        in.close();
        return c;
    }


    /**
     * 记录登录用户名
     * */
    public static void saveLoginUserName(String name) {
        userName = name;
    }

    public static String getLoginUserName() {
        return  userName;
    }

    /**
     * 记录登录密码
     * */
    public static void setUserPassword(String password) {
        userPassword = password;
    }

    public static String getUserPassword() {
        return  userPassword;
    }

    public static void setPicPath(String userPicPath) {
        picPath = userPicPath;
    }

    public static String getPicPath() {
        return picPath;
    }


    public static void saveServerIP(ServerIP user) throws Exception {
        File file = new File("src//Data//ServerIP.dat");
        if(file.exists()) {
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        ObjectOutputStream obj = new ObjectOutputStream(out);
        obj.writeObject(user);
        obj.close();
    }

    /**
     * 对象反序列化
     * */
    public static ServerIP getServerIP() throws Exception {
        File file = new File("src//Data//ServerIP.dat");
        InputStream in = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(in);
        ServerIP c = new ServerIP();
        while (in.available() > 0) {
            c = (ServerIP) ois.readObject();
        }
        ois.close();
        in.close();
        return c;
    }
}
