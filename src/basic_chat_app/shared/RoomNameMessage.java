package basic_chat_app.shared;

public class RoomNameMessage extends Message {
    public String oldName;
    public String newName;
    public String userName;

    public RoomNameMessage(String oldName, String newName, String userName) {
        this.oldName = oldName;
        this.newName = newName;
        this.userName = userName;
    }

    public String toString() {
        return String.format("[%s] %s changed the room name to [%s]", oldName, userName, newName);
    }
}
