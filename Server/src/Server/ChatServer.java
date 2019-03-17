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
import DB.DBControl;
import MainUI.MainUIControll;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Platform;

/**
 * 服务端
 * @author Pu Zhiwei
 * */
public class ChatServer {
    private static int PORT = 9999;
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
                //System.out.println(firstMessage.getTYPE());

                if(firstMessage.getTYPE() == MessageType.SIGNUP) {
                    signUp(firstMessage, output);
                } else {
                    checkUserNameAndPwd(firstMessage);
                    writers.add(output);
                    sendDesignatedUser.put(firstMessage.getName(), output);
                    sendType(firstMessage, output,MessageType.SUCCESS);
                    sendNotification(firstMessage);
                    addToList();
                }
                while (socket.isConnected()) {
                    Message inputMessage = (Message)input.readObject();
                    if(inputMessage != null) {
                        System.out.println("用户" +inputMessage.getName() +  "消息类型：" + inputMessage.getTYPE());

                        switch (inputMessage.getTYPE()) {
                            case GROUPSMS:
                                writeGroup(inputMessage);
                                break;
                            case MSG:
                                write(inputMessage);
                                break;
                            case CONNECT:
                                addToList();
                            case SIGNUP:
                                signUp(inputMessage, output);
                                break;
                            case CHANGEPWD:
                                changePassword(inputMessage, output);
                                break;
                            case HISTORY:
                                lookUpHistoryMessage(inputMessage, output);
                                break;
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
            String sql = "select * from User where Name = " + "'" + firstMessage.getName() + "'";
            if(!names.containsKey(firstMessage.getName()) && DBControl.checkUserNameAndPwd(sql, firstMessage)) {
                this.name = firstMessage.getName();
                user = new UserInformation(firstMessage.getEmail(),firstMessage.getName(),firstMessage.getPassword());
                user.setUserPicture(firstMessage.getHeadPicture());
                users.add(user);
                names.put(name, user);

            } else {
                throw new UserNameOrPwdException("密码或用户名错误！");
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


        /**
         * 发送用户上线通知
         * */
        private Message sendNotification(Message firstMessage) throws IOException {
            Message msg = new Message(firstMessage.getName(),"上线了",MessageType.NOTIFICATION);
            msg.setHeadPicture(firstMessage.getHeadPicture());
            writeGroup(msg);
            return msg;
        }

        /**
         * 关闭连接
         * */
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

        /**
         * 移除下线用户
         * */
        private Message removeFromList() throws IOException {
            Message msg = new Message("SERVER","has left the chat", MessageType.DISCONNECT);
            msg.setUserList(names);
            writeGroup(msg);
            return msg;
        }

        /**
         * 返回注册结果
         * */
        private void signUp(Message message, ObjectOutputStream output) {
            try {
                if(DBControl.signUp(message)) {
                    sendType(new Message("SERVER","SignUp Success",MessageType.SIGNUPSUCCESS),output,MessageType.SIGNUPSUCCESS);
                    System.out.println("用户：" + message.getName() + " 注册成功");
                } else {
                    sendType(new Message("SERVER","SignUp Fail",MessageType.SIGNUPFAIL),output,MessageType.SIGNUPFAIL);
                    System.out.println("用户：" + message.getName() + " 注册失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * 返回密码修改结果
         * */
        private void changePassword(Message message, ObjectOutputStream output) {
            try {
                if(DBControl.changePwd(message)) {
                    sendType(new Message("SERVER","Change Success",MessageType.CHANGESUCCESS),output,MessageType.CHANGESUCCESS);
                } else {
                    sendType(new Message("SERVER","Change Success",MessageType.CHANGEFAIL),output,MessageType.CHANGEFAIL);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void lookUpHistoryMessage(Message message, ObjectOutputStream output) {
            //TODO 待添加
        }
    }




    public static void main(String[] args) throws Exception{
        try {
            ServerMessage serverMessage = SaveServerMessage.readMessage();
            PORT = ServerMessage.PORT;
            System.out.println(ServerMessage.MYSSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startServer();
    }
}
