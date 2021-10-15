package basic_chat_app.shared;

public class MessageRequest extends Request {
    public String message;

    public MessageRequest(String message) {
        this.message = message;
    }
}
