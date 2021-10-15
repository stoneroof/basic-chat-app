package basic_chat_app.shared;

public class PrivateRoomRequest extends Request {
    public String roomName;

    public PrivateRoomRequest(String roomName) {
        this.roomName = roomName;
    }
}
