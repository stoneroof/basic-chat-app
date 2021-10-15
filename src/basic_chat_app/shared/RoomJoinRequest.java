package basic_chat_app.shared;

public class RoomJoinRequest extends Request {
    public String roomName;

    public RoomJoinRequest(String roomName) {
        this.roomName = roomName;
    }
}
