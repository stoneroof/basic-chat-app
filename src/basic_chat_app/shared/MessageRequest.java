package basic_chat_app.shared;

import basic_chat_app.client.RoomId;

public class MessageRequest extends Request {
    public long roomID;
    public String message;

    public MessageRequest(RoomId roomID, String message) {
        this.roomID = roomID.roomId;
        this.message = message;
    }

    public boolean isValid() {
        return this.message != null;
    }
}
