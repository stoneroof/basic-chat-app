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
    private ClientSharedData shared;

    public ChatClient(String ip, int port) throws Exception {
        socket = new Socket(ip, port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
    }

    // start a thread to listen for messages from the server
    private void startListener() {
        shared = new ClientSharedData();
        new Thread(new ChatClientSocketListener(socketIn, shared)).start();
    }

    private void sendRequest(Request m) throws Exception {
        socketOut.writeObject(m);
//        socketOut.flush();
    }

    private void mainLoop(Scanner in) throws Exception {
        System.out.print("Chat sessions has started - enter a user name: ");
        String name = in.nextLine().trim();

        sendRequest(new RegisterRequest(name));

        String line = in.nextLine().trim();
        while (true) {
            String lower = line.toLowerCase();

            if (lower.startsWith("/join")) {
                sendRequest(new RoomJoinRequest(line.substring(5).strip()));
            }
            else if (lower.startsWith("/private")) {
                sendRequest(new PrivateRoomRequest(line.substring(8).strip()));
            }
            else if (lower.startsWith("/accept")) {
                synchronized (shared) {
                    if (shared.invite == null) {
                        System.out.println("ERROR: No invites available");
                    } else {
                        sendRequest(new RoomJoinRequest(shared.invite));
                        shared.invite = null;
                    }
                }
            }
            else if (lower.startsWith("/quit")) {
                break;
            }
            else {
                synchronized (shared) {
                    if (!shared.connected) {
                        System.out.println("Please join a room");
                    }
                    else if (lower.startsWith("/leave")) {
                        sendRequest(new RoomLeaveRequest(shared));
                        shared.connected = false;
                    }
                    else if (lower.startsWith("/add")) {
                        sendRequest(new AddUserRequest(shared, line.substring(4).strip()));
                    }
                    else if (lower.startsWith("/rename")) {
                        sendRequest(new RoomNameRequest(shared, line.substring(7).strip()));
                    }
                    else if (lower.startsWith("/me")) {
                        sendRequest(new MessageRequest(shared, line.substring(3).strip(), true));
                    }
                    else {
                        sendRequest(new MessageRequest(shared, line, false));
                    }
                }
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
