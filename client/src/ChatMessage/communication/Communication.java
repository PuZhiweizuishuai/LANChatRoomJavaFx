package ChatMessage.communication;

import ChatMessage.Login.LoginControl;
import ChatMessage.Main.MainUIControl;
import ChatMessage.Setting.SettingUiControl;
import ChatMessage.SignUp.SignUpControl;
import ChatMessage.user.Message;
import ChatMessage.user.MessageType;
import ChatMessage.user.ServerIP;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 与服务器通信模块
 *
 * @author PuZhiwei
 */
public class Communication implements Runnable {
    private static final String HASCONNECTED = "has connected";

    public MainUIControl controller;
    public SignUpControl signUpControl;
    private static String picture;
    private Socket socket;
    public String hostname;
    public int port;
    public static String username;
    private static String userPassword;
    private static String userEmail;
    private static String userMessage;
    private static ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream input;
    private OutputStream outputStream;
    private MessageType messageType = MessageType.CONNECT;

    public Communication(String hostname, int port, String username, String picture ,MainUIControl controller, SignUpControl signUpControl) {
        this.hostname = hostname;
        this.port = port;
        Communication.username = username;
        Communication.picture = picture;
        this.controller = controller;
        this.signUpControl = signUpControl;

    }

    public void setUserMessage(String message) {
        userMessage = message;
    }

    public void setUserPassword(String password) {
        userPassword = password;
    }

    public void setEmail(String email) {
        userEmail = email;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }



    @Override
    public void run() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, port), ServerIP.timeout);
            outputStream = socket.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            is = socket.getInputStream();
            input = new ObjectInputStream(is);
        } catch (Exception e) {
            LoginControl.getInstance().showDilog("提示", "服务器异常，连接失败！");
            System.out.println("服务器异常");
            e.printStackTrace();
        }

        try {
            if(messageType == MessageType.SIGNUP) {
                signUpConnect();
            } else {
                connect();
            }
            while (socket.isConnected()) {
                Message message = null;
                message = (Message) input.readObject();
                if (message != null) {
                    System.out.println("消息类型： " + message.getTYPE());
                    switch (message.getTYPE()) {
                        case GROUPSMS:
                            controller.saveGroupMessage(message);
                            controller.addGroupMessage(message);
                            break;
                        case MSG:
                            controller.SaveMessage(message);
                            controller.setContactMessageNumber(message.getName());
                            controller.addOtherMessage(message);
                            break;
                        case NOTIFICATION:
                            controller.newUserNotification(message);
                            break;
                        case FAIL:
                            LoginControl.getInstance().showDilog("提示：", "账号或密码错误");
                            break;
                        case SUCCESS:
                            LoginControl.getInstance().LoadMain();
                            break;
                        case CONNECT:
                            controller.setUserList(message);
                            break;
                        case DISCONNECT:
                            controller.setUserList(message);
                            break;
                        case USERLIST:
                            controller.setUserList(message);
                            break;
                        case SIGNUPSUCCESS:
                            signUpControl.loadLogin(1);
                            break;
                        case SIGNUPFAIL:
                            LoginControl.getInstance().showDilog("提示：", "与已有用户名重复！");
                            break;
                        case CHANGESUCCESS:
                            LoginControl.getInstance().showDilog("提示：", "密码更改成功！");
                            break;
                        case CHANGEFAIL:
                            LoginControl.getInstance().showDilog("提示：", "请检查现密码！");
                            break;
                        case HISTORYSUCCESS:
                            controller.showHistoryMessage(message);
                            break;
                        case HISTORYFAIL:
                            LoginControl.getInstance().showDilog("提示：", "查找失败，请检查用户名");
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
     * 此方法用于发送普通消息
     */
    public static void send(Message message) throws IOException {
        oos.writeObject(message);
        oos.flush();
    }

    /**
     * 此方法用于发送连接请求
     */
    public static void connect() throws IOException {
        Message message = new Message(username, HASCONNECTED, MessageType.CONNECT);
        message.setHeadPicture(picture);
        message.setPassword(userPassword);
        oos.writeObject(message);
    }

    public static void signUpConnect() throws  IOException {
        Message message = new Message(username, "signUp", MessageType.SIGNUP);
        message.setPassword(userPassword);
        message.setEmail(userEmail);
        oos.writeObject(message);
    }

}
