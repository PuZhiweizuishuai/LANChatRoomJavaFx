package ChatMessage.communication;

import ChatMessage.user.Message;
import mycontrol.popup.PopUpUI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.rmi.server.ExportException;

public class TextComm extends Communication implements Runnable {
    private Socket socket;

    /**
     * 该线程所处理的输出流
     * */
    private ObjectOutputStream outPut;
    /**
     * 该线程处理的输入流
     * */
    private ObjectInputStream ois = null;

    public TextComm(String ServerIP, int port, String userName, String userPic) {
        super(ServerIP, port, userName, userPic);
    }

    @Override
    public void run() {
        try {
            socket = new Socket();
            SocketAddress endpoint = new InetSocketAddress(ServerIP, port);
            socket.connect(endpoint, 10000);
            ois = new ObjectInputStream(socket.getInputStream());
            outPut = new ObjectOutputStream(socket.getOutputStream());
            if(socket.isConnected()) {
                System.out.println("连接成功");
            }
            connect();
            while (socket.isConnected()) {
                //　读取来自客户端的消息
                Message message = (Message)ois.readObject();
                if(message != null) {
                    switch (message.getTYPE()) {
                        case SUCCESS:
                            loginControl.LoadMain();
                            break;
                        case FAIL:
                            new PopUpUI("错误", "登录失败！");
                            break;
                        case USERLIST:
                            mainUIControl.sendUserList(message.getUserList());
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

    @Override
    public void send(Message message) {

    }

    @Override
    public void disconnect() {

    }
}
