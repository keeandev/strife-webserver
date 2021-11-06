package me.dinozoid.websocket.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dinozoid.websocket.server.database.DatabaseHandler;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketDeserializer;
import me.dinozoid.websocket.server.packet.PacketEncoder;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.packet.implementations.SChatPacket;
import me.dinozoid.websocket.server.packet.implementations.SSoundPacket;
import me.dinozoid.websocket.server.user.User;
import org.bson.Document;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Server extends WebSocketServer {

    private HashMap<WebSocket, User> userMap = new HashMap<>();

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private ServerPacketHandler packetHandler;

    public static byte[] audio;
    public User serverUser;
    private Gson gson;

    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
        if(userMap.containsKey(conn)) {
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
        userMap.put(conn, new User(document.getString("username"), uid, document.getString("rank")));
        return builder;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(userMap.get(conn).username() + " has been connected.");
        packetHandler.sendPacket(conn, new SChatPacket(serverUser.username(), "Welcome, " + userMap.get(conn).username() + "!"));
        packetHandler.sendPacket(conn, new SSoundPacket(Server.audio));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(userMap.get(conn).username() + " has been disconnected. (" + reason + ")");
        userMap.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Packet packet = gson.fromJson(PacketEncoder.decode(message), Packet.class);
        packet.process(conn, packetHandler);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println(conn.getRemoteSocketAddress() + " An error has occurred.");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        Path path = Paths.get("src/main/resources/deez.wav");
        try {
            audio = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        userMap.put(null, serverUser = new User("Server", "-9999", "Developer"));
        databaseHandler.openConnection();
        packetHandler = new ServerPacketHandler();
        packetHandler.init();
        gson = new GsonBuilder().registerTypeAdapter(Packet.class, new ServerPacketDeserializer<Packet>(packetHandler)).create();
        System.out.println("Server has been started.");
    }

    public Gson gson() {
        return gson;
    }
    public ServerPacketHandler packetHandler() {
        return packetHandler;
    }

}
