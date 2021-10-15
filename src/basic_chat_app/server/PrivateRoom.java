package basic_chat_app.server;

import java.util.HashSet;

public class PrivateRoom extends Room {
    private HashSet<User> allowedUsers;

    @Override
    public boolean canJoin(User user) {
        synchronized (allowedUsers) {
            return allowedUsers.contains(user);
        }
    }

    public void addUser(User user) {
        synchronized (allowedUsers) {
            allowedUsers.add(user);
        }
    }

    public void removeUser(User user) {
        synchronized (allowedUsers) {
            allowedUsers.remove(user);
        }
        // TODO: Kick out users if they're currently connected
    }
}
