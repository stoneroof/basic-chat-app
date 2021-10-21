package basic_chat_app.shared;

import basic_chat_app.client.ClientSharedData;

public class RoomLeaveRequest extends Request {
    public long roomID;
    public String roomName;

    public RoomLeaveRequest(ClientSharedData clientData) {
        this.roomID = clientData.roomId;
    }

    public RoomLeaveRequest(String roomName){
        this.roomName = roomName;
    }

    public boolean isValid() {
        return this.roomID >= 0 ^ this.roomName != null; // Edward's XOR
    }
}
