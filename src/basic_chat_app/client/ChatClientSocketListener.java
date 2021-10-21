package basic_chat_app.client;

import basic_chat_app.shared.InviteMessage;
import basic_chat_app.shared.Message;
import basic_chat_app.shared.RoomJoinResponse;

import java.io.ObjectInputStream;

public class ChatClientSocketListener implements Runnable {
    private ObjectInputStream socketIn;
    private ClientSharedData shared;

    public ChatClientSocketListener(ObjectInputStream socketIn, ClientSharedData roomId) {
        this.socketIn = socketIn;
        this.shared = roomId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message msg = (Message) socketIn.readObject();

                if (msg instanceof InviteMessage) {
                    synchronized (shared) {
                        shared.invite = ((InviteMessage) msg).roomName;
                    }
                }

                if (msg instanceof RoomJoinResponse) {
                    synchronized (shared) {
                        shared.roomId = ((RoomJoinResponse) msg).roomID;
                        shared.connected = true;
                    }
                } else {
                    System.out.println(msg);
                }
            }
        } catch (Exception ex) {
            if (!(ex instanceof java.net.SocketException))
                System.out.println("Exception caught in listener - " + ex);
        } finally{
            System.out.println("Client Listener exiting");
        }
    }
}
