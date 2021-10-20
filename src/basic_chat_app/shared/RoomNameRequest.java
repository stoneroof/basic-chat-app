package basic_chat_app.shared;

import basic_chat_app.client.ClientSharedData;

public class RoomNameRequest extends Request {
    public long roomID;
    public String roomName;

    public RoomNameRequest(ClientSharedData clientData, String roomName) {
        this.roomID = clientData.roomId;
        this.roomName = roomName;
    }

    public boolean isValid() {
        return this.roomName != null;
    }
}
