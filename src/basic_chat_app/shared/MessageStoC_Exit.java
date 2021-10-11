package basic_chat_app.shared;

public class MessageStoC_Exit extends Message {
    public String userName;

    public MessageStoC_Exit(String userName) {
        this.userName = userName;
    }

    public String toString() {
        return "Exiting: " + userName;
    }

}