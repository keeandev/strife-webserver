package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;

public class CChatPacket extends Packet {

    public CChatPacket(String message) {
        super(ServerStart.server().packetHandler().getIDForPacket(CChatPacket.class));
        data.addProperty("msg", message);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {
        packetHandler.processChatPacket(user, this);
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    public String message() {
        return data.get("msg").getAsString();
    }

}
