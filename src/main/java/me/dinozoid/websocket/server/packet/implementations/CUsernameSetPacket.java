package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

public class CUsernameSetPacket extends Packet {
    public CUsernameSetPacket(String username) {
        super(ServerStart.server().packetHandler().getIDForPacket(CUsernameSetPacket.class));
        data.addProperty("username", username);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {
        packetHandler.processUsernameSetPacket(user, this);
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    public String username() {
        return data.get("username").getAsString();
    }
}
