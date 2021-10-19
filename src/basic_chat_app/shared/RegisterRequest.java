package basic_chat_app.shared;

public class RegisterRequest extends Request {
    public String userName;

    public RegisterRequest(String userName) {
        this.userName = userName;
    }

    public boolean isValid() {
        return this.userName != null;
    }
}
