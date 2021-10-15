package basic_chat_app.shared;

public class RoomNameRequest extends Request {
    public String roomName;

    public RoomNameRequest(String roomName) {
        this.roomName = roomName;
    }
}
