package me.dinozoid.websocket.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import me.dinozoid.websocket.server.Server;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.implementations.*;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        PACKETS.put(SUserUpdatePacket.class, 7);
        PACKETS.put(CUserUpdatePacket.class, 8);
        PACKETS.put(CServerCommandPacket.class, 9);
        PACKETS.put(SServerCommandPacket.class, 10);
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
        Server.LOGGER.info(user.clientUsername() + ": " + chatPacket.message());
        server.packetHandler().broadcastPacket(new SChatPacket(user, chatPacket.message()));
    }

    public void processBanStatisticPacket(User user, CBanStatisticPacket banStatisticPacket) {
        bans.add(new Ban(user, banStatisticPacket.time(), banStatisticPacket.reason()));
    }

    public void processServerCommandPacket(User user, CServerCommandPacket commandPacket) {
        switch (commandPacket.operation()) {
            case DISCONNECT_USER:
            case UNMUTE_USER:
            case MUTE_USER: {
                switch (user.rank()) {
                    case "Developer":
                    case "Admin": {
                        sendPacket(user, new SServerCommandPacket(commandPacket.operation(), "Operation successful."));
                    }
                }
            }
            case LIST_USERS: {
                sendPacket(user, new SServerCommandPacket(commandPacket.operation(), "Operation successful."));
                break;
            }
            case PACKET: {
                Packet packet = (Packet) commandPacket.request();
            }
        }
    }

    public void processUserUpdatePacket(User user, CUserUpdatePacket userUpdatePacket) {
        for (Map.Entry<String, JsonElement> entry : userUpdatePacket.values().entrySet()) {
            SUserUpdatePacket.UpdateType type = SUserUpdatePacket.UpdateType.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            switch (type) {
                case UID: {
                    switch (user.rank()) {
                        case "Developer": {
                            user.uid(value);
                            sendPacket(user, new SUserUpdatePacket(new SUserUpdatePacket.Value(SUserUpdatePacket.UpdateType.UID, value)));
                            break;
                        }
                    }
                    break;
                }
                case CLIENT_USERNAME: {
                    switch (user.rank()) {
                        case "Developer":
                        case "Admin": {
                            user.clientUsername(value);
                            sendPacket(user, new SUserUpdatePacket(new SUserUpdatePacket.Value(SUserUpdatePacket.UpdateType.CLIENT_USERNAME, value)));
                            break;
                        }
                    }
                    break;
                }
                case RANK: {
                    switch (user.rank()) {
                        case "Developer": {
                            user.rank(value);
                            sendPacket(user, new SUserUpdatePacket(new SUserUpdatePacket.Value(SUserUpdatePacket.UpdateType.RANK, value)));
                            break;
                        }
                    }
                    break;
                }
                case ACCOUNT_USERNAME: {
                    switch (user.rank()) {
                        default: {
                            user.accountUsername(value);
                            sendPacket(user, new SUserUpdatePacket(new SUserUpdatePacket.Value(SUserUpdatePacket.UpdateType.ACCOUNT_USERNAME, value)));
                            break;
                        }
                    }
                    break;
                }
            }
        }
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
