package me.dinozoid.client.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.ClientStart;
import me.dinozoid.ServerStart;
import me.dinozoid.client.Client;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketEncoder;
import me.dinozoid.server.packet.implementations.*;
import org.java_websocket.client.WebSocketClient;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class ClientPacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    public void init() {
        PACKETS.put(CAuthenticationSentPacket.class, 0);
        PACKETS.put(SAuthenticationResponsePacket.class, 1);
        PACKETS.put(ChatPacket.class, 2);
        PACKETS.put(CBanStatisticPacket.class, 3);
        PACKETS.put(SSendSoundPacket.class, 4);
    }

    public void sendPacket(Client client, Packet packet) {
        client.send(PacketEncoder.encode(client.gson().toJson(packet)));
    }
    public void processAuthenticationResponsePacket(SAuthenticationResponsePacket authenticationSentPacket) {

    }

    public void processChatPacket(ChatPacket chatPacket) {
        System.out.println(chatPacket.message());
    }

    public void processSendSoundPacket(SSendSoundPacket sendSoundPacket) {
        AudioData audioData = new AudioData(sendSoundPacket.bytes().getBytes());
        AudioDataStream audioStream = new AudioDataStream(audioData);
        AudioPlayer.player.start(audioStream);
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }

}
