package basic_chat_app.server;

public class PublicRoom extends Room {
    public PublicRoom(String name) {
        super(name);
    }

    @Override
    public boolean canJoin(User user) {
        return true;
    }
}
