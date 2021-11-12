package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;

public class CBanStatisticPacket extends Packet {

    public CBanStatisticPacket(String reason, long time) {
        super(ServerStart.server().packetHandler().getIDForPacket(CBanStatisticPacket.class));
        data.addProperty("reason", reason);
        data.addProperty("time", time);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {
        packetHandler.processBanStatisticPacket(user, this);
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    public long time() {
        return data.get("time").getAsLong();
    }

    public String reason() {
        return data.get("reason").getAsString();
    }
}
