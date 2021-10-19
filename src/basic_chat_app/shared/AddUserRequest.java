package basic_chat_app.shared;

public class AddUserRequest extends Request {
    public long roomID;

    public String userName;

    public AddUserRequest(long roomID, String userName) {
        this.roomID = roomID;
        this.userName = userName;
    }
}
