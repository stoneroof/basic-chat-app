package basic_chat_app.shared;

public class LeaveMessage extends Message {
    public String userName;
    public String roomName;

    public LeaveMessage(String userName, String roomName) {
        this.userName = userName;
        this.roomName = roomName;
    }

    public String toString() {
        return userName + " left " + roomName;
    }
}