package basic_chat_app.shared;

public class ErrorMessage {
    public String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String toString() {
        return error;
    }
}