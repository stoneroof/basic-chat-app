package basic_chat_app.server;

public class PublicRoom extends Room {
    @Override
    public boolean canJoin(User user) {
        return true;
    }
}
