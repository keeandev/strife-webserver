package me.dinozoid.websocket.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.websocket.server.Server;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.implementations.*;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;

public class ServerPacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    private Server server;

    public ServerPacketHandler(Server server) {
        this.server = server;
    }

    public void init() {
        PACKETS.put(SChatPacket.class, 0);
        PACKETS.put(CChatPacket.class, 1);
        PACKETS.put(CBanStatisticPacket.class, 2);
        PACKETS.put(SSoundPacket.class, 3);
        PACKETS.put(SVLAbusePacket.class, 4);
        PACKETS.put(STitlePacket.class, 5);
    }

    public void sendPacket(User user, Packet packet) {
        user.socket().send(PacketEncoder.encode(server.gson().toJson(packet)));
    }

    public void sendPacket(WebSocketClient client, Packet packet) {
        client.send(PacketEncoder.encode(server.gson().toJson(packet)));
    }

    public void broadcastPacketToAllExcept(Packet packet, User user) {
        List<WebSocket> clients = new ArrayList<>(server.getConnections());
        clients.remove(user.socket());
        server.broadcast(PacketEncoder.encode(server.gson().toJson(packet)), clients);
    }

    public void broadcastPacket(Packet packet) {
        server.broadcast(PacketEncoder.encode(server.gson().toJson(packet)));
    }

    public void processChatPacket(User user, CChatPacket chatPacket) {
        server.packetHandler().broadcastPacket(new SChatPacket(user, chatPacket.message()));
    }

    public void processBanStatisticPacket(User user, CBanStatisticPacket banStatisticPacket) {
        System.out.println(user.username() + " was banned at: " + banStatisticPacket.time() + " for " + banStatisticPacket.reason());
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }

}
