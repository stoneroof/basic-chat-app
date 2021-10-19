package basic_chat_app.shared;

public class ChatMessage extends Message {
    public String userName;
    public String message;
    public String room;

    public ChatMessage(String userName, String message, String room) {
        this.userName = userName;
        this.message = message;
        this.room = room;
    }

    public String toString() {
        return "In room " + room + " chat message from " + userName + ": " + message;
    }
}
