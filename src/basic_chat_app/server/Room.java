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
        for (User user : connectedUsers) {
            user.getOut().writeObject(message);
        }
    }
    
    public void connectUser(User user) throws IOException {
        if (canJoin(user)) {
            if (connectedUsers.add(user)) {
                user.getOut().writeObject(new RoomJoinResponse(getID()));
                send(new JoinMessage(user.getUserName(), getName()));
            } else {
                user.getOut().writeObject(new ErrorMessage("Already in room"));
            }
        } else {
            user.getOut().writeObject(new ErrorMessage("Access denied"));
        }
    }

    public boolean disconnectUser(User user) throws IOException {
        if (connectedUsers.remove(user)) {
            send(new LeaveMessage(user.getUserName(), getName()));
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
}
