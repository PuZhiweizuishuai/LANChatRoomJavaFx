package ChatMessage.communication;

import ChatMessage.Login.LoginControl;
import ChatMessage.Main.MainUIControl;
import ChatMessage.SignUp.SignUpControl;
import ChatMessage.user.Message;
import ChatMessage.user.MessageType;
import ChatMessage.user.SaveUser;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 与服务器通信模块
 * @author PuZhiwei
 * */
public abstract class Communication {
    /**
     * 获取各 UI 控制器对象
     * */
    protected MainUIControl mainUIControl = MainUIControl.getInstance();
    protected LoginControl loginControl = LoginControl.getInstance();
    protected SignUpControl signUpControl = SignUpControl.getInstance();

    protected String userName;
    protected String userPic;
    protected String ServerIP;
    protected int port;
    public Communication(String serverIP, int port, String userName, String userPic) {
        this.ServerIP = serverIP;
        this.port = port;
        this.userName = userName;
        this.userPic = userPic;
        // 将通信对象注册到 mainUIControl 控制器中
        mainUIControl.setConnection(this);
    }

    /**
     * 发送，由子类具体实现
     * */
    abstract void send(Message message);

    /**
     * 断开物理连接，由子类具体实现
     * */
    public void destory() {
        //TODO 待写入日志
    }

    /**
     * 用户连接请求消息
     * */
    public void connect() {
        LinkedList<String> usertemp = new LinkedList<>();
        usertemp.add(userName);
        //创建新连接消息
        Message message = new Message(userName,"",MessageType.CONNECT);
        message.setUserList(usertemp);
        // 发送
        send(message);
    }

    /**
     * 用户注销请求
     * */
    public void disconnect() {
        Message message = new Message(userName,"",MessageType.DISCONNECT);
        send(message);
    }

    /**
     * 对话类型消息
     * */
    public void sendMsg(String from, String to, String conntent) {
        Message message = new Message(from, conntent,MessageType.MSG);
        message.setTo(to);
        send(message);
    }

    /**
     * 查询用户列表
     * */
    public void querUserList() {
        Message message = new Message(userName,"",MessageType.QUERY);
        message.setTo(userName);
        send(message);
    }

}
