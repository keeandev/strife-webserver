package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import org.java_websocket.WebSocket;

public class CBanStatisticPacket extends Packet {
    public CBanStatisticPacket(String reason, long time) {
        super(2);
        data.addProperty("reason", reason);
        data.addProperty("time", time);
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler packetHandler) {
        packetHandler.processBanStatisticPacket(ws, this);
    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {

    }

    public long time() {
        return data.get("time").getAsLong();
    }

    public String reason() {
        return data.get("reason").getAsString();
    }
}
