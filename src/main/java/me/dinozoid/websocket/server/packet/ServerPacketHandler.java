package me.dinozoid.websocket.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.implementations.*;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;

public class ServerPacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    public void init() {
        PACKETS.put(SChatPacket.class, 0);
        PACKETS.put(CChatPacket.class, 1);
        PACKETS.put(CBanStatisticPacket.class, 2);
        PACKETS.put(SSoundPacket.class, 3);
    }

    public void sendPacket(WebSocket ws, Packet packet) {
        ws.send(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)));
    }

    public void sendPacket(WebSocketClient client, Packet packet) {
        client.send(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)));
    }

    public void broadcastPacketToAllExcept(Packet packet, WebSocket user) {
        List<WebSocket> clients = new ArrayList<>(ServerStart.server().getConnections());
        clients.remove(user);
        ServerStart.server().broadcast(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)), clients);
    }

    public void broadcastPacket(Packet packet) {
        ServerStart.server().broadcast(PacketEncoder.encode(ServerStart.server().gson().toJson(packet)));
    }

    public void processChatPacket(User user, CChatPacket chatPacket) {
        System.out.println(user.username()  + " said: " + chatPacket.message());
        ServerStart.server().packetHandler().broadcastPacket(new SChatPacket(user, chatPacket.message()));
    }

    public void processBanStatisticPacket(WebSocket ws, CBanStatisticPacket banStatisticPacket) {
        System.out.println(ws.getRemoteSocketAddress() + " was banned at: " + banStatisticPacket.time() + " for " + banStatisticPacket.reason());
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }

}
