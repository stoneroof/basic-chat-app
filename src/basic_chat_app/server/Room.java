package basic_chat_app.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;

import basic_chat_app.shared.JoinMessage;
import basic_chat_app.shared.LeaveMessage;
import basic_chat_app.shared.Message;

public abstract class Room implements Serializable {
    private HashSet<User> connectedUsers;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void send(Message message) throws IOException {
        synchronized (connectedUsers) {
            for (User user : connectedUsers) {
                user.getOut().writeObject(message);
            }
        }
    }
    
    public boolean connectUser(User user) throws IOException {
        if (canJoin(user)) {
            synchronized (connectedUsers) {
                if (connectedUsers.add(user)) {
                    send(new JoinMessage(user.getName()));
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void disconnectUser(User user) throws IOException {
        synchronized (connectedUsers) {
            if (connectedUsers.remove(user)) {
                send(new LeaveMessage(user.getName()));
            }
        }
    }

    public abstract boolean canJoin(User user);
}
