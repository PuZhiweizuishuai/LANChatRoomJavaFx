package ChatMessage.user;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 封装的消息类
 * */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private MessageType TYPE;
    private String message;
    private String to;
    private String password;
    private LinkedList<String> userList;
    public Message(String name, String message, MessageType TYPE) {
        this.name = name;
        this.message = message;
        this.TYPE = TYPE;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setTo(String other) {
        to = other;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
    public MessageType getTYPE() {
        return TYPE;
    }

    public LinkedList<String> getUserList() {
        return userList;
    }

    public void setUserList(LinkedList<String> userList) {
        this.userList = userList;
    }
}
