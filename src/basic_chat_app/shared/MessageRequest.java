package basic_chat_app.shared;

public class MessageRequest extends Request {
    public long roomID;
    public String message;

    public MessageRequest(long roomID, String message) {
        this.roomID = roomID;
        this.message = message;
    }

    public boolean isValid() {
        return this.message != null;
    }
}
