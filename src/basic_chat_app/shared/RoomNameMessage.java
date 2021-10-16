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
        return userName + " changed the room name from " + oldName + " to " + newName;
    }
}
