package basic_chat_app.server;

import basic_chat_app.shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.List;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;

public class ChatServerSocketListener implements Runnable {
    private static long lastID = 0L;

    private Socket socket;

    private User user;
    private final List<User> users;
    private final Map<Long, Room> rooms;

    public ChatServerSocketListener(Socket socket, List<User> users, Map<Long, Room> rooms) {
        this.socket = socket;
        this.users = users;
        this.rooms = rooms;
    }

    private void setup() throws Exception {
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        String name = socket.getInetAddress().getHostName();

        user = new User(socket, socketIn, socketOut, name);
        users.add(user);

        System.out.println("added client " + name);
    }

    private Room findRoomByName(String name) {
        synchronized (rooms) {
            return rooms.values().stream().filter(room -> room.getName().equals(name)).findAny().orElse(null);
        }
    }

    private void purgeEmptyRooms() {
        int size;
        synchronized (rooms) {
            size = rooms.size();
            rooms.entrySet().removeIf(entry -> {
                Room room = entry.getValue();
                return room.numberOfConnectedUsers() == 0;
            });
            size -= rooms.size();
            System.out.printf("Purged %d room(s)\n", size);
        }
    }

    private static synchronized long nextRoomID() {
        if (lastID == Long.MAX_VALUE) {
            return -1; // we're out of IDs
        }
        return lastID++;
    }

    private void addRoom(Room room) {
        rooms.put(room.getID(), room);
    }

    @Override
    public void run() {
        try {
            setup();
            ObjectInputStream in = user.getInput();

            RegisterRequest registerRequest = (RegisterRequest)in.readObject();
            user.setUserName(registerRequest.userName);

            while (true) {
                Request request = (Request) in.readObject();
                if (!request.isValid()) {
                    user.getOut().writeObject(new ErrorMessage("Malformed request"));
                    continue;
                }
                if (request instanceof QuitRequest) {
                    break;
                }
                else if (request instanceof RoomJoinRequest) {
                    synchronized (rooms) {
                        Room room = findRoomByName(((RoomJoinRequest) request).roomName);
                        if (room == null) {
                            long id = nextRoomID();
                            if (id < 0) {
                                user.getOut().writeObject(new ErrorMessage("Room limit reached"));
                            } else {
                                room = new PublicRoom(nextRoomID(), ((RoomJoinRequest) request).roomName);
                                addRoom(room);
                            }
                        }
                        room.connectUser(user);
                    }
                }
                else if (request instanceof RoomLeaveRequest) {
                    Room room = rooms.get(((RoomLeaveRequest) request).roomID);
                    if (room != null) {
                        room.disconnectUser(user);
                    } else {
                        user.getOut().writeObject(new ErrorMessage("Room does not exist"));
                    }
                    purgeEmptyRooms();
                }
                else if (request instanceof RoomNameRequest) {
                    Room room = rooms.get(((RoomNameRequest) request).roomID);
                    String name = ((RoomNameRequest) request).roomName;
                    if (room != null) {
                        synchronized (rooms) {
                            if (findRoomByName(name) == null) {
                                String oldName = room.getName();
                                room.setName(name);
                                room.send(new RoomNameMessage(oldName, name, user.getUserName()));
                            } else {
                                user.getOut().writeObject(new ErrorMessage("Name taken"));
                            }
                        }
                    } else {
                        user.getOut().writeObject(new ErrorMessage("Room does not exist"));
                    }
                }
                else if (request instanceof PrivateRoomRequest) {
                    String name = ((PrivateRoomRequest) request).roomName;
                    synchronized (rooms) {
                        if (findRoomByName(name) == null) {
                            long id = nextRoomID();
                            if (id < 0) {
                                user.getOut().writeObject(new ErrorMessage("Room limit reached"));
                            } else {
                                PrivateRoom room = new PrivateRoom(id, name);
                                addRoom(room);
                                room.addUser(user);
                            }
                        } else {
                            user.getOut().writeObject(new ErrorMessage("Name taken"));
                        }
                    }
                }
                else if (request instanceof AddUserRequest) {
                    Room room = rooms.get(((AddUserRequest) request).roomID);
                    if (room == null) {
                        user.getOut().writeObject("Room does not exist");
                    } else if (room.hasUser(user)) {
                        if (room instanceof PrivateRoom) {
                            String username = ((AddUserRequest) request).userName;
                            Optional<User> userToAdd;
                            synchronized (users) {
                                userToAdd = users.stream().filter(u -> username.equals(u.getUserName())).findFirst();
                            }
                            if (userToAdd.isPresent()) {
                                ((PrivateRoom) room).addUser(userToAdd.get());
                                userToAdd.get().getOut().writeObject(new InviteMessage(user.getUserName(), room.getName()));
                            } else {
                                user.getOut().writeObject(new ErrorMessage("Could not find user"));
                            }
                        } else {
                            user.getOut().writeObject(new ErrorMessage("Room is not a private room"));
                        }
                    } else {
                        user.getOut().writeObject(new ErrorMessage("Not in the room"));
                    }
                }
                else if (request instanceof MessageRequest) {
                    Room room = rooms.get(((MessageRequest) request).roomID);
                    if (room == null) {
                        user.getOut().writeObject("Room does not exist");
                    } else if (room.hasUser(user)) {
                        room.send(new ChatMessage(user.getUserName(), ((MessageRequest) request).message, room.getName()));
                    } else {
                        user.getOut().writeObject(new ErrorMessage("Not in the room"));
                    }
                }
                else {
                    user.getOut().writeObject(new ErrorMessage("Unhandled message type: " + request.getClass()));
                }
            }
        } catch (Exception ex) {
            if (ex instanceof SocketException) {
                System.out.println("Caught socket ex for " + 
                    user.getHostName());
            } else {
                System.out.println(ex);
                ex.printStackTrace();
            }
        } finally {
            users.remove(user);

            synchronized (rooms) {
                for (Room room: rooms.values()) {
                    if (room.hasUser(user)) {
                        try {
                            room.disconnectUser(user);
                        } catch (IOException ex) {}
                    }
                }
            }

            purgeEmptyRooms();

            try {
                user.getSocket().close();
            } catch (IOException ex) {}
        }
    }
        
}
