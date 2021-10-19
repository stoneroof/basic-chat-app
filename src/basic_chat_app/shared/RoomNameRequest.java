package basic_chat_app.shared;

public class RoomNameRequest extends Request {
    public long roomID;
    public String roomName;

    public RoomNameRequest(long roomID, String roomName) {
        this.roomID = roomID;
        this.roomName = roomName;
    }

    public boolean isValid() {
        return this.roomName != null;
    }
}
