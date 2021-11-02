package me.dinozoid.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dinozoid.server.database.DatabaseHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketDeserializer;
import me.dinozoid.server.packet.PacketEncoder;
import me.dinozoid.server.packet.PacketHandler;
import me.dinozoid.server.packet.implementations.AuthenticationSendPacket;
import me.dinozoid.server.user.User;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class Server extends WebSocketServer {

    private HashMap<WebSocket, User> userMap = new HashMap<>();

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private PacketHandler packetHandler = new PacketHandler();
    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getRemoteSocketAddress() + " has been connected.");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn.getRemoteSocketAddress() + " has been disconnected. (" + reason + ")");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(conn.getRemoteSocketAddress() + " has sent: " + message);
        Gson gson = new GsonBuilder().registerTypeAdapter(Packet.class, new PacketDeserializer<Packet>(packetHandler)).create();
        Packet packet = gson.fromJson(PacketEncoder.decode(message), Packet.class);
        if(packet instanceof AuthenticationSendPacket) {
            System.out.println("chat packet xd");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println(conn.getRemoteSocketAddress() + " An error has occurred.");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
//        databaseHandler.openConnection();
        packetHandler.init();
        System.out.println("Server has been started.");
    }

}
