package basic_chat_app.shared;

public class InviteMessage extends Message {
    public String userName;
    public String roomName;

    public InviteMessage(String userName, String roomName) {
        this.userName = userName;
        this.roomName = roomName;
    }

    public String toString() {
        return userName + " invited you to " + roomName;
    }
}