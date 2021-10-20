package basic_chat_app.shared;

public class ChatMessage extends Message {
    public String userName;
    public String message;
    public String room;
    public boolean me;

    public ChatMessage(String userName, String message, String room, boolean me) {
        this.userName = userName;
        this.message = message;
        this.room = room;
        this.me = me;
    }

    public String toString() {
        if (me) {
            return String.format("[%s] *%s %s", room, userName, message);
        } else {
            return String.format("[%s] %s: %s", room, userName, message);
        }
    }
}
