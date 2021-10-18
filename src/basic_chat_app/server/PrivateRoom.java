package basic_chat_app.server;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PrivateRoom extends Room {
    private final Set<User> allowedUsers = Collections.synchronizedSet(new HashSet<>());

    public PrivateRoom(long id, String name) {
        super(id, name);
    }

    @Override
    public boolean canJoin(User user) {
        return allowedUsers.contains(user);
    }

    public boolean addUser(User user) throws IOException {
        return allowedUsers.add(user);
    }

    public boolean removeUser(User user) throws IOException {
        if (allowedUsers.remove(user)) {
            disconnectUser(user);
            return true;
        } else {
            return false;
        }
    }
}
