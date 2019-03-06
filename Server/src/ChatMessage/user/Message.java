package ChatMessage.user;

import java.io.Serializable;

/**
 * 封装的消息类
 * */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    private String message;

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
