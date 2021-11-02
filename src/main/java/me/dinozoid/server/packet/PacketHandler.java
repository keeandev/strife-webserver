package me.dinozoid.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.server.Server;
import me.dinozoid.server.packet.implementations.*;
import org.java_websocket.WebSocket;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class PacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    public void init() {
        PACKETS.put(SAuthenticationSentPacket.class, 0);
        PACKETS.put(CAuthenticationResponsePacket.class, 1);
        PACKETS.put(CChatPacket.class, 2);
        PACKETS.put(CBanStatisticPacket.class, 3);
        PACKETS.put(SSendSoundPacket.class, 4);
    }

    public void processAuthenticationSendPacket(WebSocket ws, SAuthenticationSentPacket authenticationResponsePacket) {

    }

    public void processAuthenticationResponsePacket(WebSocket ws, CAuthenticationResponsePacket authenticationSentPacket) {

    }

    public void processChatPacket(WebSocket ws, CChatPacket chatPacket) {
        ws.send("you sent: " + chatPacket.message());
        System.out.println(ws.getRemoteSocketAddress() + " has sent: " + chatPacket.message());
        if(chatPacket.message().contains("deez")) {
            ws.send(Server.audio);
        }
    }

    public void processBanStatisticPacket(WebSocket ws, CBanStatisticPacket banStatisticPacket) {
        ws.send("You sent a ban stat packet.");
        System.out.println(ws.getRemoteSocketAddress() + " was banned at: " + banStatisticPacket.time() + " for " + banStatisticPacket.reason());
    }

    public void processSendSoundPacket(WebSocket ws, SSendSoundPacket sendSoundPacket) {
        ws.send("You sent a sound packet.");
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
