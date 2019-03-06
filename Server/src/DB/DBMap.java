package DB;

import ChatMessage.user.Message;
import Server.RequestProcess;

import java.util.HashMap;

public class DBMap {
    public static HashMap<Message, RequestProcess> dbMap = new HashMap<>();
}
