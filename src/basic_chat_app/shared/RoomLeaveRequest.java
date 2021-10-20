package basic_chat_app.shared;

import basic_chat_app.client.ClientSharedData;

public class RoomLeaveRequest extends Request {
    public long roomID;

    public RoomLeaveRequest(ClientSharedData clientData) {
        this.roomID = clientData.roomId;
    }

    public boolean isValid() {
        return true;
    }
}
