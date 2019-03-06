package ChatMessage.user;
import java.io.*;

public class SaveUser {
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
}
