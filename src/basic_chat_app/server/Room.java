package basic_chat_app.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;

import basic_chat_app.shared.*;

public abstract class Room implements Serializable {
    private final HashSet<User> connectedUsers = new HashSet<>();
    private final long id;
    private String name;

    public Room(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getID() {
        return id;
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
                    user.getOut().writeObject(new RoomJoinResponse(getID()));
                    send(new JoinMessage(user.getHostName()));
                } else {
                    user.getOut().writeObject(new ErrorMessage("Already in room"));
                }
            }
        } else {
            user.getOut().writeObject(new ErrorMessage("Access denied"));
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
