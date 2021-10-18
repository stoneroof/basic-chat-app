package basic_chat_app.server;

import java.io.IOException;
import java.util.HashSet;

public class PrivateRoom extends Room {
    private final HashSet<User> allowedUsers = new HashSet<>();

    public PrivateRoom(long id, String name) {
        super(id, name);
    }

    @Override
    public boolean canJoin(User user) {
        synchronized (allowedUsers) {
            return allowedUsers.contains(user);
        }
    }

    public boolean addUser(User user) throws IOException {
        synchronized (allowedUsers) {
            return allowedUsers.add(user);
        }
    }

    public boolean removeUser(User user) throws IOException {
        synchronized (allowedUsers) {
            if (allowedUsers.remove(user)) {
                disconnectUser(user);
                return true;
            } else {
                return false;
            }
        }
    }
}
