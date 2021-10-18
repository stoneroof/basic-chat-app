package basic_chat_app.shared;

public class JoinMessage extends Message {
    public String userName;
    public String roomName;

    public JoinMessage(String userName, String roomName) {
        this.userName = userName;
        this.roomName = roomName;
    }

    public String toString() {
        return userName + " joined " + roomName;
    }
}