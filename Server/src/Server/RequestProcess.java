package Server;

import ChatMessage.user.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestProcess extends Thread {
    private Socket socket;
    private ObjectInputStream oInputStream;
    private ObjectOutputStream oOutputStream;

    public RequestProcess(Socket s) {
        try {
            socket = s;
            oInputStream = new ObjectInputStream(socket.getInputStream());
            oOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message message = (Message)oInputStream.readObject();
            oOutputStream.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if(!socket.isClosed()) {
                try {
                    Message message = (Message)oInputStream.readObject();
                    String info = message.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
