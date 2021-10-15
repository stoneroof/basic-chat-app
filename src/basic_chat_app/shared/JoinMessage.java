package basic_chat_app.shared;

public class JoinMessage extends Message {
    public String userName;

    public JoinMessage(String userName) {
        this.userName = userName;
    }
}