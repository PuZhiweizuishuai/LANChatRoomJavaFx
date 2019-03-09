package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ChatMessage.exception.UserNameOrPwdException;
import ChatMessage.user.Message;
import ChatMessage.user.MessageType;
import ChatMessage.user.UserInformation;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

/**
 * 服务端
 * @author Pu Zhiwei
 * */
public class ChatServer {
    private static final int PORT = 9999;
    private static HashSet<ObjectOutputStream> writers = new HashSet<>();
    private static ArrayList<UserInformation> users = new ArrayList<>();
    private static HashMap<String, UserInformation> names = new HashMap<>();
    private static HashMap<String, ObjectOutputStream> sendDesignatedUser = new HashMap<>();
    /**
     * 启动监听服务
     * */
    public static void startServer() throws Exception{
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                System.out.println("accept之前");
                // 造成阻塞,等待连接
                new Handler(server.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private UserInformation user;
        private ObjectInputStream input;
        private OutputStream os;
        private ObjectOutputStream output;
        private InputStream is;

        public Handler(Socket socket) throws IOException {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                is = socket.getInputStream();
                input = new ObjectInputStream(is);
                os = socket.getOutputStream();
                output = new ObjectOutputStream(os);

                Message firstMessage = (Message)input.readObject();
                checkUserNameAndPwd(firstMessage);
                writers.add(output);
                sendDesignatedUser.put(firstMessage.getName(), output);
                sendType(firstMessage, output,MessageType.SUCCESS);
                addToList();

                while (socket.isConnected()) {
                    Message inputMessage = (Message)input.readObject();
                    if(inputMessage != null) {
                        System.out.println("消息类型：" + inputMessage.getTYPE());
                        System.out.println("用户" +inputMessage.getName() + "说："+ inputMessage.getMessage());
                        switch (inputMessage.getTYPE()) {
                            case GROUPSMS:
                                writeGroup(inputMessage);
                                break;
                            case MSG:
                                write(inputMessage);
                                break;
                            case CONNECT:
                                addToList();
                            default:
                                break;
                        }
                    }
                }
            } catch (Exception e) {
               try {
                   sendType(new Message("SERVER","",MessageType.FAIL), output,MessageType.FAIL);
               } catch (IOException e1) {
                   e1.printStackTrace();
               }
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        }

        private synchronized void checkUserNameAndPwd(Message firstMessage) throws UserNameOrPwdException {
            if(!names.containsKey(firstMessage.getName())) {
                this.name = firstMessage.getName();
                user = new UserInformation(firstMessage.getEmail(),firstMessage.getName(),firstMessage.getPassword());
                user.setUserPicture(firstMessage.getHeadPicture());
                users.add(user);
                names.put(name, user);

            } else {
                throw new UserNameOrPwdException("密码错误！");
            }
        }

        private Message sendType(Message firstMessage, ObjectOutputStream output, MessageType type) throws IOException {
            Message msg = new Message(firstMessage.getName(),"",type);
            msg.setHeadPicture(firstMessage.getHeadPicture());
            output.writeObject(msg);
            output.reset();
            return msg;
        }

        private Message addToList() throws IOException {
            Message message = new Message("SERVER","",MessageType.CONNECT);
            writeGroup(message);
            return message;
        }

        /**
         * 向指定用户发送消息
         * */
        private void write(Message message) throws IOException {
            ObjectOutputStream oos = sendDesignatedUser.get(message.getTo());
            System.out.println("用户：" + message.getName() + " 开始向用户：" + message.getTo() + "发送消息！");
            oos.writeObject(message);
            oos.reset();
        }

        /**
         * 群发
         * */
        private void writeGroup(Message message) throws IOException {
            for(ObjectOutputStream writer: writers) {
                System.out.println("开始群发 " + writer);
                message.setUserList(names);
                writer.writeObject(message);
                writer.reset();
            }
        }

        private synchronized void closeConnections() {
            if(name != null) {
                names.remove(name);
                System.out.println("用户"+ name + "下线");
            }
            if(user != null) {
                users.remove(user);
                System.out.println("对象"+ user + "下线");
            }
            if(output != null) {
                writers.remove(output);
                System.out.println("对象"+ output + "下线");
            }

            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                removeFromList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Message removeFromList() throws IOException {
            Message msg = new Message("SERVER","has left the chat", MessageType.DISCONNECT);
            msg.setUserList(names);
            writeGroup(msg);
            return msg;
        }
    }




    public static void main(String[] args) throws Exception{
        startServer();
    }
}
