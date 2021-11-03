package me.dinozoid.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dinozoid.client.packet.ClientPacketDeserializer;
import me.dinozoid.client.packet.ClientPacketHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketEncoder;
import me.dinozoid.server.packet.ServerPacketDeserializer;
import me.dinozoid.server.packet.implementations.CBanStatisticPacket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

import java.net.URI;
import java.nio.ByteBuffer;

public class Client extends WebSocketClient {

    private ClientPacketHandler packetHandler;

    public Client(URI serverUri) {
        super(serverUri);
    }

    private Gson gson;

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        packetHandler = new ClientPacketHandler();
        packetHandler.init();
        gson = new GsonBuilder().registerTypeAdapter(Packet.class, new ClientPacketDeserializer<>(packetHandler)).create();
        packetHandler.sendPacket(this, new CBanStatisticPacket("gay", 86400000));
    }

    @Override
    public void onMessage(String message) {
        Packet packet = gson.fromJson(PacketEncoder.decode(message), Packet.class);
        packet.process(this, packetHandler);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }

    public Gson gson() {
        return gson;
    }
    public ClientPacketHandler packetHandler() {
        return packetHandler;
    }
}
