package basic_chat_app.shared;

public class RoomJoinResponse extends Message {
    public long roomID;

    public RoomJoinResponse(long roomID) {
        this.roomID = roomID;
    }
}
