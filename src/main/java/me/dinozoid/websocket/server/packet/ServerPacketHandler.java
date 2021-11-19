package me.dinozoid.websocket.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.websocket.server.Server;
import me.dinozoid.websocket.server.packet.implementations.*;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerPacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    private Server server;

    public ServerPacketHandler(Server server) {
        this.server = server;
    }

    public void init() {
        PACKETS.put(SUserConnectPacket.class, 0);
        PACKETS.put(SChatPacket.class, 1);
        PACKETS.put(CChatPacket.class, 2);
        PACKETS.put(CBanStatisticPacket.class, 3);
        PACKETS.put(SSoundPacket.class, 4);
        PACKETS.put(STitlePacket.class, 5);
        PACKETS.put(SRetardFuckerPacket.class, 6);
        PACKETS.put(CUsernameSetPacket.class, 7);
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
        Server.LOGGER.info(user.username() + ": " + chatPacket.message());
        server.packetHandler().broadcastPacket(new SChatPacket(user, chatPacket.message()));
    }

    public void processBanStatisticPacket(User user, CBanStatisticPacket banStatisticPacket) {
        bans.add(new Ban(user, banStatisticPacket.time(), banStatisticPacket.reason()));
    }

    public void processUsernameSetPacket(User user, CUsernameSetPacket usernameSetPacket) {
        user.clientUsername(usernameSetPacket.username());
    }

    private List<Ban> bans = new ArrayList<>();

    private class Ban {
        private long time;
        private User user;
        private String reason;

        public Ban(User user, long time, String reason) {
            this.user = user;
            this.time = time;
            this.reason = reason;
        }

        public long time() {
            return time;
        }
        public User user() {
            return user;
        }
        public String reason() {
            return reason;
        }
    }

    public List<Ban> bans() {
        return bans;
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }

}
