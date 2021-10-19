package basic_chat_app.shared;

import basic_chat_app.client.RoomId;

public class RoomLeaveRequest extends Request {
    public long roomID;

    public RoomLeaveRequest(RoomId roomID) {
        this.roomID = roomID.roomId;
    }

    public boolean isValid() {
        return true;
    }
}
