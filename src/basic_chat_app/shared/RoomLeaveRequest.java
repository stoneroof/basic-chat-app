package basic_chat_app.shared;

public class RoomLeaveRequest extends Request {
    public long roomID;

    public RoomLeaveRequest(long roomID) {
        this.roomID = roomID;
    }
}
