package me.dinozoid.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.server.packet.implementations.SAuthenticationSentPacket;
import me.dinozoid.server.packet.implementations.CAuthenticationResponsePacket;
import me.dinozoid.server.packet.implementations.CChatPacket;
import org.java_websocket.WebSocket;

public class PacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    public void init() {
        PACKETS.put(SAuthenticationSentPacket.class, 0);
        PACKETS.put(CAuthenticationResponsePacket.class, 1);
        PACKETS.put(CChatPacket.class, 2);
    }

    public void processAuthenticationSendPacket(WebSocket ws, SAuthenticationSentPacket authenticationResponsePacket) {

    }

    public void processAuthenticationResponsePacket(WebSocket ws, CAuthenticationResponsePacket authenticationSentPacket) {

    }

    public void processChatPacket(WebSocket ws, CChatPacket chatPacket) {
        ws.send("you sent: " + chatPacket.message());
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }

}
