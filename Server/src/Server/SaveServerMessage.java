package Server;

import ChatMessage.user.UserInformation;

import java.io.*;

public class SaveServerMessage {
    public static void save(ServerMessage serverMessage) throws Exception {
        File file = new File("src//Data//Server.dat");
        if(file.exists()) {
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        ObjectOutputStream obj = new ObjectOutputStream(out);
        obj.writeObject(serverMessage);
        obj.close();
    }

    /**
     * 对象反序列化
     * */
    public static ServerMessage readMessage() throws Exception {
        File file = new File("src//Data//Server.dat");
        InputStream in = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(in);
        ServerMessage c = new ServerMessage();
        while (in.available() > 0) {
            c = (ServerMessage)ois.readObject();
        }
        ois.close();
        in.close();
        return c;
    }
}
