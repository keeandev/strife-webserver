package me.dinozoid.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.ServerStart;
import me.dinozoid.server.packet.implementations.*;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.util.Collection;

public class ServerPacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    public void init() {
        PACKETS.put(CAuthenticationSentPacket.class, 0);
        PACKETS.put(SAuthenticationResponsePacket.class, 1);
        PACKETS.put(ChatPacket.class, 2);
        PACKETS.put(CBanStatisticPacket.class, 3);
        PACKETS.put(SSendSoundPacket.class, 4);
    }

    public void sendPacket(WebSocket ws, Packet packet) {
        ws.send(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)));
    }

    public void sendPacket(WebSocketClient client, Packet packet) {
        client.send(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)));
    }

    public void broadcastPacketToAllExcept(Packet packet, WebSocket user) {
        Collection<WebSocket> clients = ServerStart.server().getConnections();
        clients.remove(user);
        ServerStart.server().broadcast(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)), clients);
    }

    public void broadcastPacket(Packet packet) {
        ServerStart.server().broadcast(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)));
    }

    public void processAuthenticationSentPacket(WebSocket ws, CAuthenticationSentPacket authenticationSentPacket) {

    }

    public void processChatPacket(WebSocket ws, ChatPacket chatPacket) {
//        Collection<WebSocket> clients = ServerStart.server().getConnections();
//        clients.remove(ws);
        System.out.println(ws.getRemoteSocketAddress()  + " said: " + chatPacket.message());
        ServerStart.server().broadcast(chatPacket.message());
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
