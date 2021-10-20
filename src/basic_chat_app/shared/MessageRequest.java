package basic_chat_app.shared;

import basic_chat_app.client.ClientSharedData;

public class MessageRequest extends Request {
    public long roomID;
    public String message;

    public MessageRequest(ClientSharedData clientData, String message) {
        this.roomID = clientData.roomId;
        this.message = message;
    }

    public boolean isValid() {
        return this.message != null;
    }
}
