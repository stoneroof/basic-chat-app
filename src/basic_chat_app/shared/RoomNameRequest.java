package basic_chat_app.shared;

import basic_chat_app.client.RoomId;

public class RoomNameRequest extends Request {
    public long roomID;
    public String roomName;

    public RoomNameRequest(RoomId roomID, String roomName) {
        this.roomID = roomID.roomId;
        this.roomName = roomName;
    }

    public boolean isValid() {
        return this.roomName != null;
    }
}
