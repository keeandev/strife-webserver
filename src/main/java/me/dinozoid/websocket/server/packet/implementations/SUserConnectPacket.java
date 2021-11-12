package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

public class SUserConnectPacket extends Packet {

    public SUserConnectPacket(User user) {
        super(ServerStart.server().packetHandler().getIDForPacket(SUserConnectPacket.class));
        data.addProperty("user", ServerStart.server().gson().toJson(user));
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processUserConnectPacket(this);
    }

    public User user() {
        return ServerStart.server().gson().fromJson(data.get("user").getAsString(), User.class);
    }

}
