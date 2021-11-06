package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;

public class SVLAbusePacket extends Packet {

    public SVLAbusePacket(float value) {
        super(4);
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
