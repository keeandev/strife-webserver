package me.dinozoid.websocket.client.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.PacketEncoder;
import me.dinozoid.websocket.server.packet.implementations.*;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class ClientPacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    public void init() {
        PACKETS.put(SChatPacket.class, 0);
        PACKETS.put(CChatPacket.class, 1);
        PACKETS.put(CBanStatisticPacket.class, 2);
        PACKETS.put(SSoundPacket.class, 3);
        PACKETS.put(SRetardFuckerPacket.class, 4);
        PACKETS.put(STitlePacket.class, 5);
    }

    public void sendPacket(Client client, Packet packet) {
        client.send(PacketEncoder.encode(client.gson().toJson(packet)));
    }

    public void processChatPacket(SChatPacket chatPacket) {
        System.out.println("[" + chatPacket.user().username() + "] " + chatPacket.message());
    }

    public void processVLAbusePacket(SRetardFuckerPacket abusePacket) {
        // TODO: set y val :)
    }

    public void processTitlePacket(STitlePacket titlePacket) {
        // TODO: process title
    }

    public void processSendSoundPacket(SSoundPacket sendSoundPacket) {
        AudioData audioData = new AudioData(sendSoundPacket.bytes());
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
