package basic_chat_app.server;

public class PublicRoom extends Room {
    public PublicRoom(long id, String name) {
        super(id, name);
    }

    @Override
    public boolean canJoin(User user) {
        return true;
    }
}
