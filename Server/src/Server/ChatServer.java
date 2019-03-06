package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import ChatMessage.user.Message;
/**
 * 服务端
 * @author Pu Zhiwei
 * */
public class ChatServer {

    private static HashMap<String, Socket> socketsfromUserName = new HashMap<>();
    /**
     * 启动监听服务
     * */
    public static void startServer() throws Exception{
        try {
            ServerSocket server = new ServerSocket(9999);
            while (true) {
                System.out.println("accept之前");
                // 造成阻塞,等待连接
                Socket socket = server.accept();
                System.out.println(socket);
                // 每当客户端连接后启动一条ServerThread线程为该客户端服务
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ServerThread implements Runnable {
        /**
         * 定义当前线程所处理的Socket
         * */
        private Socket s = null;

        /**
         * 输出流
         * */
        private ObjectInputStream ois = null;

        /**
         * 消息记录
         * */
        private Message message = null;
        public ServerThread(Socket s) throws Exception {
            this.s = s;
            ois = new ObjectInputStream(s.getInputStream());
            message = (Message)ois.readObject();
        }

        @Override
        public void run() {
            try {
                if(!s.isClosed()) {
                    System.out.println(s + "用户已连接服务器！下一步将判断是否能登录成功..");
                }
                while (s.isConnected()) {
                    //读取客户端消息
                    String revString = message.getMessage();
                    if(revString != null) {
                        //System.out.println("收到消息");
                        //System.out.println(message.getTYPE());
                        switch (message.getTYPE()) {
                            case CONNECT:
                                checkConnect(message);
                                break;
                            case MSG:
                                sendAll(message, false);
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 将message发送给指定客户端
         * */
        private void send(Message message, Socket socket) throws Exception {
            OutputStream outPut =new ObjectOutputStream(socket.getOutputStream());
            ((ObjectOutputStream) outPut).writeObject(message);
            System.out.println("消息发送成功！");
        }

        /**
         * 将message发送给所有客户端
         * */
        private void sendAll(Message message, Boolean isRemoveLocalUser) throws Exception {
            OutputStream outPut = null;
            if(isRemoveLocalUser) {
                //TODO
            } else {
                for(Socket socket : socketsfromUserName.values()) {
                    outPut =new ObjectOutputStream(socket.getOutputStream());
                    ((ObjectOutputStream) outPut).writeObject(message);
                }
            }
            System.out.println("数据发送成功！");
        }

        /**
         * 检查是否登录成功，并发送登录结果
         * 登陆成功后需要向所有用户发送新用户集列表和新用户上线通知
         */
        private void checkConnect(Message message) throws IOException{
            String username = message.getName();

            if(socketsfromUserName.containsKey(username)) {
                socketsfromUserName.put(username, s);
                System.out.println(username);
            }

        }
    }

    public static void main(String[] args) throws Exception{
        startServer();
    }
}
