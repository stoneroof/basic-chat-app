package basic_chat_app.shared;

import basic_chat_app.client.ClientSharedData;

public class MessageRequest extends Request {
    public long roomID;
    public String message;
    public boolean me;

    public MessageRequest(ClientSharedData clientData, String message, boolean me) {
        this.roomID = clientData.roomId;
        this.message = message;
        this.me = me;
    }

    public boolean isValid() {
        return this.message != null;
    }
}
