package basic_chat_app.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import basic_chat_app.shared.*;

public abstract class Room implements Serializable {
    private final Set<User> connectedUsers = Collections.synchronizedSet(new HashSet<>());
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
        send(message, null);
    }

    public void send(Message message, User exclude) throws IOException {
        for (User user : connectedUsers) {
            if (user != exclude) {
                user.getOut().writeObject(message);
            }
        }
    }
    
    public void connectUser(User user) throws IOException {
        if (canJoin(user)) {
            user.getOut().writeObject(new RoomJoinResponse(getID()));
            if (connectedUsers.add(user)) {
                send(new JoinMessage(user.getUserName(), getName()));
                System.out.printf("User %s joined %s\n", user, this);
            }
        } else {
            user.getOut().writeObject(new ErrorMessage("Access denied"));
        }
    }

    public boolean disconnectUser(User user) throws IOException {
        if (connectedUsers.remove(user)) {
            send(new LeaveMessage(user.getUserName(), getName()));
            System.out.printf("User %s left %s\n", user, this);
            return true;
        } else {
            return false;
        }
    }

    public boolean hasUser(User user) {
        return connectedUsers.contains(user);
    }

    public int numberOfConnectedUsers() {
        return connectedUsers.size();
    }

    public abstract boolean canJoin(User user);

    public String toString() {
        return String.format("\"%s\" (ID: %d)", getName(), getID());
    }
}
