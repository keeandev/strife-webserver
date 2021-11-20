package me.dinozoid.websocket.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dinozoid.websocket.server.database.DatabaseHandler;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.PacketEncoder;
import me.dinozoid.websocket.server.packet.ServerPacketDeserializer;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.packet.implementations.*;
import me.dinozoid.websocket.server.user.User;
import me.dinozoid.websocket.server.user.UserHandler;
import org.bson.Document;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {

    private ServerPacketHandler packetHandler = new ServerPacketHandler(this);
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private UserHandler userHandler = new UserHandler();

    public static Logger LOGGER = LoggerFactory.getLogger("Server");

    private User serverUser;
    private Gson gson;

    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
        if(userHandler.userMap().containsKey(conn)) {
            throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, "Not accepted!");
        }
        if(request.getResourceDescriptor().length() > 100) {
            throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, "Not accepted!");
        }
        if(!request.getResourceDescriptor().startsWith("/?uid=") && !request.getResourceDescriptor().contains("&hwid=")) {
            throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, "Not accepted!");
        }
        String descriptorCopy = request.getResourceDescriptor().replace("/?uid=", "");
        String uid = descriptorCopy.substring(0, descriptorCopy.indexOf("&"));
        String hwid = descriptorCopy.replace(uid, "").replace("&hwid=", "");
        Document document = databaseHandler.getUserByUID(uid);
        if(!document.getString("hwid").equals(hwid)) {
            throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, "Not accepted!");
        }
        User user;
        userHandler.addUser(conn, user = new User(document.getString("username"), uid, document.getString("rank")));
        return builder;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        User user = userHandler.userBySocket(conn);
        System.out.println(user.clientUsername() + " has been connected.");
        packetHandler.sendPacket(user, new SUserConnectPacket(user));
        packetHandler.sendPacket(user, new SChatPacket(serverUser, "Welcome, " + user.clientUsername() + "!"));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        User user = userHandler.userBySocket(conn);
        System.out.println(user.clientUsername() + " has been disconnected. (" + reason + ")");
        userHandler.removeUser(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        User user = userHandler.userBySocket(conn);
        Packet packet = gson.fromJson(PacketEncoder.decode(message), Packet.class);
        if(packet != null) {
            System.out.println(packet);
            packet.process(user, packetHandler);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println(conn.getRemoteSocketAddress() + " An error has occurred.");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        userHandler.addUser(null, serverUser = new User("Server", "-9999", "Developer"));
        databaseHandler.openConnection();
        packetHandler.init();
        gson = new GsonBuilder().registerTypeAdapter(Packet.class, new ServerPacketDeserializer<Packet>(packetHandler)).create();
        System.out.println("Server has been started.");
    }

    public User serverUser() {
        return serverUser;
    }
    public Gson gson() {
        return gson;
    }
    public ServerPacketHandler packetHandler() {
        return packetHandler;
    }
    public UserHandler userHandler() {
        return userHandler;
    }
}
