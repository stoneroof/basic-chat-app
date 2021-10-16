package basic_chat_app.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;

import basic_chat_app.shared.JoinMessage;
import basic_chat_app.shared.LeaveMessage;
import basic_chat_app.shared.Message;

public abstract class Room implements Serializable {
    private final HashSet<User> connectedUsers = new HashSet<>();
    private String name;

    public Room(String name) {
        this.name = name;
    }

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
    
    public void connectUser(User user) throws IOException {
        if (canJoin(user)) {
            synchronized (connectedUsers) {
                if (connectedUsers.add(user)) {
                    // TODO: Send success message
                    send(new JoinMessage(user.getHostName()));
                } else {
                    // TODO: Send "already in room" message
                }
            }
        } else {
            // TODO: Send "access denied" message
        }
    }

    public boolean disconnectUser(User user) throws IOException {
        synchronized (connectedUsers) {
            if (connectedUsers.remove(user)) {
                send(new LeaveMessage(user.getHostName()));
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean hasUser(User user) {
        synchronized (connectedUsers) {
            return connectedUsers.contains(user);
        }
    }

    public int numberOfConnectedUsers() {
        synchronized (connectedUsers) {
            return connectedUsers.size();
        }
    }

    public abstract boolean canJoin(User user);
}
