package Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
                InputStream inputStream = socket.getInputStream();
                Scanner in = new Scanner(inputStream);
                while (in.hasNext()) {
                    String line = in.nextLine();
                    System.out.println(line);
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
