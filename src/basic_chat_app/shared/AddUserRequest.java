package basic_chat_app.shared;

import basic_chat_app.client.ClientSharedData;

public class AddUserRequest extends Request {
    public long roomID;

    public String userName;

    public AddUserRequest(ClientSharedData clientData, String userName) {
        this.roomID = clientData.roomId;
        this.userName = userName;
    }

    public boolean isValid() {
        return this.userName != null;
    }
}
