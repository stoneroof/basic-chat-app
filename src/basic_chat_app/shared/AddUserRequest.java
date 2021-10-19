package basic_chat_app.shared;

import basic_chat_app.client.RoomId;

public class AddUserRequest extends Request {
    public long roomID;

    public String userName;

    public AddUserRequest(RoomId roomID, String userName) {
        this.roomID = roomID.roomId;
        this.userName = userName;
    }

    public boolean isValid() {
        return this.userName != null;
    }
}
