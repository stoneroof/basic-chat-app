package basic_chat_app.shared;

public class AddUserRequest extends Request {
    public String userName;

    public AddUserRequest(String userName) {
        this.userName = userName;
    }
}
