package basic_chat_app.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    public static final int PORT = 54323;

    private static final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private static final Map<Long, Room> rooms = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(100);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║  WELCOME TO THE BASIC CHAT APP SERVER!  ║");
            System.out.println("║   Thank you for your purchase of this   ║");
            System.out.println("║   Java-based chat server software. We   ║");
            System.out.println("║    hope you enjoy using our product!    ║");
            System.out.println("║     (no technical support provided)     ║");
            System.out.println("║                                         ║");
            System.out.println("║            Alexandra Volkova            ║");
            System.out.println("║               Anthony Li                ║");
            System.out.println("║              Edward Feng                ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Local IP:   " + Inet4Address.getLocalHost().getHostAddress());
            System.out.println("Local Port: " + serverSocket.getLocalPort());

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.printf("Connected to %s:%d on local port %d\n", socket.getInetAddress(),
                            socket.getPort(), socket.getLocalPort());

                    // handle client business in another thread
                    pool.execute(new ChatServerSocketListener(socket, users, rooms));
                } 
                
                // prevent exceptions from causing server from exiting.
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }
    }
}
