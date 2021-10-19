package basic_chat_app.client;

import basic_chat_app.shared.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private RoomId roomId;

    public ChatClient(String ip, int port) throws Exception {
        socket = new Socket(ip, port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
    }

    // start a thread to listen for messages from the server
    private void startListener() {
        new Thread(new ChatClientSocketListener(socketIn, roomId)).start();
    }

    private void sendRequest(Request m) throws Exception {
        socketOut.writeObject(m);
//        socketOut.flush();
    }

    private void mainLoop(Scanner in) throws Exception {
        roomId = new RoomId();

        System.out.print("Chat sessions has started - enter a user name: ");
        String name = in.nextLine().trim();

        sendRequest(new RegisterRequest(name));

        String line = in.nextLine().trim();
        while (true) {
            if (line.toLowerCase().startsWith("/join")) {
                sendRequest(new RoomJoinRequest(line));
            }
            else if (line.toLowerCase().startsWith("/quit")) {
                break;
            }
            else if (!roomId.connected) {
                System.out.println("Please join a room");
            }
            else if (line.toLowerCase().startsWith("/leave")){
                sendRequest(new RoomLeaveRequest(roomId));
                roomId.connected = false;
            }
            else {
                sendRequest(new MessageRequest(roomId, line));
            }
            line = in.nextLine().trim();
        }
        sendRequest(new QuitRequest());

    }

    private void closeSockets() throws Exception {
        socketIn.close();
        socketOut.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What's the server IP? ");
        String serverip = userInput.nextLine();

        System.out.println("What's the server port? ");
        int port = userInput.nextInt();
        userInput.nextLine();

        ChatClient cc = new ChatClient(serverip, port);

        cc.startListener();
        cc.mainLoop(userInput);

        userInput.close();
        cc.closeSockets();
    }

}
