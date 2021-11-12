package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;

public class SRetardFuckerPacket extends Packet {

    public SRetardFuckerPacket(float value) {
        super(ServerStart.server().packetHandler().getIDForPacket(SRetardFuckerPacket.class));
        data.addProperty("value", value);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processVLAbusePacket(this);
    }

    public float value() {
        return data.get("value").getAsFloat();
    }

}
