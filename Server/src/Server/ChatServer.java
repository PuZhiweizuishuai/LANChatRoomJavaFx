package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import ChatMessage.user.Message;
/**
 * 服务端
 * @author Pu Zhiwei
 * */
public class ChatServer {
    /**
     * 启动监听服务
     * */
    public static void startServer() {
        try {
            ServerSocket server = new ServerSocket(9999);
            while (true) {
                System.out.println("accept之前");
                // 等待连接
                Socket socket = server.accept();
                System.out.println(socket);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                try {
                    Message message = (Message)ois.readObject();
                    System.out.println("用户："+message.getName() +"    发送消息："+ message.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startServer();
    }
}
