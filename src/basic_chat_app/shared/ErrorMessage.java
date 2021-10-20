package basic_chat_app.shared;

public class ErrorMessage extends Message {
    public String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String toString() {
        return String.format("ERROR: %s", error);
    }
}
