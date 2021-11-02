package me.dinozoid.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.server.packet.implementations.CBanStatisticPacket;
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
        PACKETS.put(CBanStatisticPacket.class, 3);
    }

    public void processAuthenticationSendPacket(WebSocket ws, SAuthenticationSentPacket authenticationResponsePacket) {

    }

    public void processAuthenticationResponsePacket(WebSocket ws, CAuthenticationResponsePacket authenticationSentPacket) {

    }

    public void processChatPacket(WebSocket ws, CChatPacket chatPacket) {
        ws.send("you sent: " + chatPacket.message());
        System.out.println(ws.getRemoteSocketAddress() + " has sent: " + chatPacket.message());
    }

    public void processBanStatisticPacket(WebSocket ws, CBanStatisticPacket banStatisticPacket) {
        ws.send("You sent a ban stat packet.");
        System.out.println(ws.getRemoteSocketAddress() + " was banned at: " + banStatisticPacket.time() + " for " + banStatisticPacket.reason());
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }

}
