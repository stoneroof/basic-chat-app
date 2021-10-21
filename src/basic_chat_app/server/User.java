package basic_chat_app.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream out;
    private String hostName;
    private String userName;

    public User(Socket socket, ObjectInputStream input, ObjectOutputStream out, String name) {
        this.socket = socket;
        this.input = input;
        this.out = out;
        this.hostName = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return String.format("%s (%s)", getHostName(), getUserName());
    }
    
}