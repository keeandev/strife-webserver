package me.dinozoid.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketDeserializer;
import me.dinozoid.server.packet.PacketEncoder;
import me.dinozoid.server.packet.PacketHandler;
import me.dinozoid.server.packet.implementations.CBanStatisticPacket;
import me.dinozoid.server.packet.implementations.CChatPacket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

import java.net.URI;
import java.nio.ByteBuffer;

public class Client extends WebSocketClient {

    private PacketHandler packetHandler;

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        packetHandler = new PacketHandler();
        packetHandler.init();
        Gson gson = new GsonBuilder().registerTypeAdapter(Packet.class, new PacketDeserializer<Packet>(packetHandler)).create();
        send(PacketEncoder.encode(gson.toJson(new CChatPacket("deez"))));
        send(PacketEncoder.encode(gson.toJson(new CBanStatisticPacket("gay", 86400000))));
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        AudioData audioData = new AudioData(bytes.array());
        AudioDataStream audioStream = new AudioDataStream(audioData);
        AudioPlayer.player.start(audioStream);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }

}
