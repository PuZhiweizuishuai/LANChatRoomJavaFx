package ChatMessage.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 封装的消息类
 * */
public class Message implements Serializable {
    private String name;
    private MessageType TYPE;
    private String message;
    private String to;
    private String password;
    private ArrayList<UserInformation> userList;
    private String email;
    private String headPicture;
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

    public void setTYPE(MessageType TYPE) {
        this.TYPE = TYPE;
    }

    public MessageType getTYPE() {
        return TYPE;
    }

    public ArrayList<UserInformation> getUserList() {
        return userList;
    }

    public void setUserList(HashMap<String, UserInformation> userList) {
        this.userList = new ArrayList<>(userList.values());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getTo() {
        return to;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public String getHeadPicture() {
        return headPicture;
    }
}
