package me.dinozoid.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dinozoid.server.database.DatabaseHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.ServerPacketDeserializer;
import me.dinozoid.server.packet.PacketEncoder;
import me.dinozoid.server.packet.ServerPacketHandler;
import me.dinozoid.server.packet.implementations.SSendSoundPacket;
import me.dinozoid.server.user.User;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Server extends WebSocketServer {

    private HashMap<WebSocket, User> userMap = new HashMap<>();

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private ServerPacketHandler packetHandler;

    public static byte[] audio;
    private Gson gson;

    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getRemoteSocketAddress() + " has been connected.");
        packetHandler.sendPacket(conn, new SSendSoundPacket(Server.audio));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn.getRemoteSocketAddress() + " has been disconnected. (" + reason + ")");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Packet packet = gson.fromJson(PacketEncoder.decode(message), Packet.class);
        packet.process(conn, packetHandler);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {

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
//        databaseHandler.openConnection();
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
