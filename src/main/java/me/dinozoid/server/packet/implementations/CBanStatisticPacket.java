package me.dinozoid.server.packet.implementations;

import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketHandler;
import org.java_websocket.WebSocket;

public class CBanStatisticPacket extends Packet {
    public CBanStatisticPacket(String reason, long time) {
        super(3);
        data.addProperty("reason", reason);
        data.addProperty("time", time);
    }

    @Override
    public void process(WebSocket ws, PacketHandler packetHandler) {
        packetHandler.processBanStatisticPacket(ws, this);
    }

    public long time() {
        return data.get("time").getAsLong();
    }

    public String reason() {
        return data.get("reason").getAsString();
    }
}
