package basic_chat_app.shared;

public class LeaveMessage extends Message {
    public String userName;

    public LeaveMessage(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return userName + " left";
    }
}