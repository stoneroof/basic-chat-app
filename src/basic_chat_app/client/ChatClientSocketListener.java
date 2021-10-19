package basic_chat_app.client;

import basic_chat_app.shared.Message;
import basic_chat_app.shared.RoomJoinResponse;

import java.io.ObjectInputStream;

public class ChatClientSocketListener implements Runnable {
    private ObjectInputStream socketIn;
    private RoomId roomId;

    public ChatClientSocketListener(ObjectInputStream socketIn, RoomId roomId) {
        this.socketIn = socketIn;
        this.roomId = roomId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message msg = (Message) socketIn.readObject();
                if (msg instanceof RoomJoinResponse) {
                    synchronized(roomId) {
                        roomId.roomId = ((RoomJoinResponse) msg).roomID;
                    }
                } else {
                    System.out.println(msg);
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception caught in listener - " + ex);
        } finally{
            System.out.println("Client Listener exiting");
        }
    }
}
